package nl.dylandavid.project2.duoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bmi::class, BloodPressure::class], version = 2, exportSchema = false)
abstract class ProjectDatabase: RoomDatabase() {
    abstract fun bmiDao(): BmiDao
    abstract fun bpDao(): BpDao

    companion object{
        @Volatile
        private var INSTANCE: ProjectDatabase? = null


        fun getDatabase(context: Context): ProjectDatabase{
        val tempInstance = INSTANCE;
            if (tempInstance != null){
                return tempInstance
            }
             synchronized(this){
                val instance = Room.databaseBuilder(
                context.applicationContext,
                ProjectDatabase::class.java,
              "duo_project_database"
                 ).fallbackToDestructiveMigration().build()
                 INSTANCE = instance
                return instance
            }
        }
    }
}