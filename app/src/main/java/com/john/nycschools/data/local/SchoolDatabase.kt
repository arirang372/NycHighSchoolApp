package com.john.nycschools.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolDetails

/**
 *  Database object that can store the [School] and [SchoolDetails] table
 *
 */
@Database(entities = [School::class, SchoolDetails::class], version = 1, exportSchema = false)
abstract class SchoolDatabase : RoomDatabase() {

    abstract fun schoolDao(): SchoolDao

    abstract fun schoolDetailsDao(): SchoolDetailsDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: SchoolDatabase? = null

        fun getInstance(context: Context): SchoolDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildDatabase(context: Context): SchoolDatabase {
            return Room.databaseBuilder(
                context, SchoolDatabase::class.java,
                "SchoolDatabase.db"
            ).build()
        }
    }
}