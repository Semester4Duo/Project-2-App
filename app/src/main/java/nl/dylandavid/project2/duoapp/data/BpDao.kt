package nl.dylandavid.project2.duoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BpDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBp(bp: BloodPressure)

    @Query("SELECT * FROM bp_table ORDER BY id ASC LIMIT 1")
    fun readBp(): LiveData<BloodPressure>

    @Update
    suspend fun updateBp(bp:BloodPressure)
}