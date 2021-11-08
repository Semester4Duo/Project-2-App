package nl.dylandavid.project2.duoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "bp_table")
data class BloodPressure (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val bpUp: String,
    val bpLow: String,
    val heartRate: String,
    val lastUpdate: String,
    val lastUpdateHeartRate: String
){

}