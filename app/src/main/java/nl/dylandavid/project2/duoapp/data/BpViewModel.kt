package nl.dylandavid.project2.duoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BpViewModel(application: Application) : AndroidViewModel(application){

    val readBp: LiveData<BloodPressure>
    private val repository: BpRepository

    init {
        val bpDao = ProjectDatabase.getDatabase(application).bpDao()
        repository = BpRepository(bpDao)
        readBp = repository.readBp
    }

    fun addBp(bp: BloodPressure){
        viewModelScope.launch(Dispatchers.IO){
            repository.addBp(bp)
        }
    }
    fun updateBp(bp: BloodPressure){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateBp(bp)
        }
    }

}