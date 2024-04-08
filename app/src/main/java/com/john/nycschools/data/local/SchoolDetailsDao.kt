package com.john.nycschools.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.john.nycschools.models.SchoolDetails

/**
 *  Data access object to store [SchoolDetails] object
 *
 */
@Dao
interface SchoolDetailsDao {

    @Query("SELECT * FROM school_details WHERE dbn_id = :dbn")
    fun getSchoolDetailsByDbn(dbn: String): SchoolDetails

    @Upsert
    suspend fun upsert(school: SchoolDetails)

    @Query("DELETE FROM school_details WHERE dbn_id = :dbn")
    suspend fun deleteSchoolDetailsById(dbn: String)

}