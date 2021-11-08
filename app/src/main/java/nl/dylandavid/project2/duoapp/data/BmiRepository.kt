package nl.dylandavid.project2.duoapp.data

import androidx.lifecycle.LiveData

class BmiRepository (private val bmiDao: BmiDao){
    val readBmi: LiveData<Bmi> = bmiDao.readBmi()

    suspend fun addBmi(bmi: Bmi){
        bmiDao.addBmi(bmi)
    }

    suspend fun updateBmi(bmi: Bmi){
        bmiDao.updateBmi(bmi)
    }
}