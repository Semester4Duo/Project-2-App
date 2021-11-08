package nl.dylandavid.project2.duoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BmiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBmi(bmi: Bmi)

    @Query("SELECT * FROM bmi_table ORDER BY id ASC LIMIT 1")
    fun readBmi(): LiveData<Bmi>

    @Update
    suspend fun updateBmi(bmi:Bmi)
}