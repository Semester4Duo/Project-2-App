package nl.dylandavid.project2.duoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "bmi_table")
data class Bmi (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val bmi: String,
    val weight: String,
    val wc: String,
    val lastUpdate: String
    ){

}