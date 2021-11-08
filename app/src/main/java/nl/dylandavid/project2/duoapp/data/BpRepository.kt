package nl.dylandavid.project2.duoapp.data

import androidx.lifecycle.LiveData

class BpRepository (private val bpDao: BpDao){
    val readBp: LiveData<BloodPressure> = bpDao.readBp()

    suspend fun addBp(bp: BloodPressure){
        bpDao.addBp(bp)
    }

    suspend fun updateBp(bp: BloodPressure){
        bpDao.updateBp(bp)
    }
}