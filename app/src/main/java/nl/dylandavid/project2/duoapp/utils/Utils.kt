package nl.dylandavid.project2.duoapp.utils

import android.os.Handler
import io.reactivex.functions.Action

object Utils {
    fun delay(secs: Int, action: Action) {
        val handler = Handler()
        handler.postDelayed(
            { action.run() },
            secs * 1000L
        )
    }
}