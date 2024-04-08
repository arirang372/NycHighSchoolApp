package com.john.nycschools.data.remote

import com.john.nycschools.models.School
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  A remote data source that handles retrieving the remote data
 *
 */

@Singleton
class SchoolRemoteDataSource @Inject constructor(
    private val service: NycSchoolService
){
    fun loadAllSchools(): Flow<List<School>> {
        return flow {
            emit(
                service.getAllSchools()
            )
        }
    }

    fun loadSchoolsByBoro(boro: String): Flow<List<School>> {
        return flow {
            emit(
                service.getSchoolsByBoro(boro)
            )
        }
    }

    fun loadSchoolDetails(school: School): Flow<School> {
        return flow {
            val details = service.getSchoolDetails(school.dbn)
            school.details = if(details.isNullOrEmpty()) null else details[0]
            emit(
                school
            )
        }
    }
}