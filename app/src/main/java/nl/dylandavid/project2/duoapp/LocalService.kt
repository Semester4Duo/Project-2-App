package nl.dylandavid.project2.duoapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import io.reactivex.Completable
import io.reactivex.functions.Action
import nl.dylandavid.project2.duoapp.utils.Utils
import java.util.*

class LocalService() : Service() {
    private var hubConnection: HubConnection

    private var onConnectAction = arrayListOf({})

    private var onDisconnectAction = arrayListOf({})

    private var onMessageReceivedAction = arrayListOf({})

    private val initWaitTime = 2

    private val Tag = "SignalR_Service"

    private lateinit var connectionTask: Completable

    init {
        hubConnection = HubConnectionBuilder.create("https://gp.kurza.nl/").build()
    }
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    inner class LocalBinder : Binder() {
        val service: LocalService
            get() = this@LocalService
    }

    override fun onCreate() {
        Toast.makeText(this, "Connecting...", Toast.LENGTH_SHORT).show()
        connect()
    }

    private fun connect(){
        hubConnection.start()
            .doOnComplete{
                Looper.prepare()
                onConnectAction.first().invoke()
                Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
            }
            .doOnError{
                retryConnection(5, it)
            }.blockingAwait()
    }

    private fun waitUntilConnected(){
        while(hubConnection.connectionState!=HubConnectionState.CONNECTED){
        }
    }

    private fun retryConnection(tryAmount: Int, error: Throwable){
        retryConnection(tryAmount, tryAmount, error)
    }

    private fun retryConnection(tryAmount: Int, initTryAmount: Int, error: Throwable){
        Log.w(Tag, "Couldn't connect to SignalR Server retrying in $initWaitTime seconds")
        Utils.delay(initWaitTime){
            if(tryAmount>0){
                Looper.prepare()
                hubConnection.start()
                    .doOnComplete{
                        onConnectAction.first().invoke()
                        Looper.prepare()
                        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
                    }
                    .doOnError{
                        Toast.makeText(this, "Retrying Connection...", Toast.LENGTH_SHORT).show()
                        Looper.prepare()
                        retryConnection(tryAmount-1, initTryAmount, error)
                    }.blockingAwait()
            } else {
                throw error
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // Tell the user we stopped.
        hubConnection.stop()
        Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private val mBinder: IBinder = LocalBinder()

    fun setOnConnect(action: Action) {
        when(hubConnection.connectionState){
            HubConnectionState.CONNECTED -> {
                if(Looper.myLooper() == Looper.getMainLooper()){
                    val thread = Thread {
                        action.run()
                    }
                    thread.start()
                } else {
                    action.run()
                }
            }
            HubConnectionState.CONNECTING -> {
                onConnectAction = arrayListOf({action.run()})
            }
            HubConnectionState.DISCONNECTED -> {
                onConnectAction = arrayListOf({action.run()})
            }
        }
    }

    fun setOnDisconnect(action: Action) {
        when(hubConnection.connectionState){
            HubConnectionState.DISCONNECTED -> {
                if(Looper.myLooper() == Looper.getMainLooper()){
                    val thread = Thread {
                        action.run()
                    }
                    thread.start()
                } else {
                    action.run()
                }
                hubConnection.onClosed { action.run() }
            }
            HubConnectionState.CONNECTING -> {
                hubConnection.onClosed { action.run() }
            }
            HubConnectionState.CONNECTED -> {
                hubConnection.onClosed { action.run() }
            }
        }
    }

    fun setOnNewConference(action: Action1<String>){
        hubConnection.on("HostConference",
            {
            action.invoke(it)
            }, String::class.java)
    }

    fun setOnEndConference(action: Action1<String>){
        hubConnection.on("EndConference",
            {
                action.invoke(it)
            }, String::class.java)
    }

    fun hostCall(conferenceId: String): String{
        hubConnection.invoke("HostCall", conferenceId)
        return conferenceId
    }

    fun joinCall(conferenceId: String){
        hubConnection.invoke("JoinCall", conferenceId)
    }

    fun endCall(conferenceId: String){
        hubConnection.invoke("EndCall", conferenceId)
    }
}