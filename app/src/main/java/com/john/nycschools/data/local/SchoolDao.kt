package com.john.nycschools.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.john.nycschools.models.School

/**
 *  Data access object to store [School] object
 *
 */
@Dao
interface SchoolDao {
    @Query("SELECT * FROM schools")
    fun getAllSchools(): List<School>

    @Upsert
    suspend fun upsert(school: School)

    @Query("DELETE FROM schools WHERE dbn = :dbn")
    suspend fun deleteSchoolById(dbn: String)

    @Query("DELETE FROM schools")
    suspend fun deleteAllSchools()

    @Upsert
    suspend fun upsertAll(schools: List<School>)
}