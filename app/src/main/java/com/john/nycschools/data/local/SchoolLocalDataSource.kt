package com.john.nycschools.data.local

import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Local Data source object that handles store [School] and [SchoolDetails]
 *
 */

@Singleton
class SchoolLocalDataSource @Inject constructor(
    private val schoolDao: SchoolDao,
    private val schoolDetailsDao: SchoolDetailsDao
) {

    suspend fun getAllSchools(): Flow<List<School>> {
        return flow {
            emit(schoolDao.getAllSchools())
        }
    }

    suspend fun saveSchool(school: School, schoolDetails: SchoolDetails) {
        schoolDao.upsert(school)
        schoolDetailsDao.upsert(schoolDetails)
    }

    suspend fun saveAllSchools(schools: List<School>): Flow<Unit> = flow {
        schoolDao.upsertAll(schools)
        emit(Unit)
    }

    suspend fun deleteAllSchools(): Flow<Unit> = flow {
        schoolDao.deleteAllSchools()
        emit(Unit)
    }
}