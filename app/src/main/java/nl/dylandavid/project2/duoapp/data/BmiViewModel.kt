package nl.dylandavid.project2.duoapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BmiViewModel(application: Application) : AndroidViewModel(application){

    val readBmi: LiveData<Bmi>
    private val repository: BmiRepository

    init {
        val bmiDao = ProjectDatabase.getDatabase(application).bmiDao()
        repository = BmiRepository(bmiDao)
        readBmi = repository.readBmi
    }

    fun addBmi(bmi: Bmi){
        viewModelScope.launch(Dispatchers.IO){
            repository.addBmi(bmi)
        }
    }
    fun updateBmi(bmi:Bmi){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateBmi(bmi)
        }
    }

}