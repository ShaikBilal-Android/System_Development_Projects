package com.example.resume_collector.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Resume::class], version = 1, exportSchema = false)
abstract class ResumeDatabase : RoomDatabase() {

    abstract fun resumeDao(): ResumeDao

    companion object {
        @Volatile
        private var INSTANCE: ResumeDatabase? = null

        fun getDatabase(context: Context): ResumeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResumeDatabase::class.java,
                    "resume_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
