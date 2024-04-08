package com.john.nycschools.data

import com.john.nycschools.data.local.SchoolLocalDataSource
import com.john.nycschools.data.remote.SchoolRemoteDataSource
import com.john.nycschools.models.School
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  A Repository that is exposed to ViewModel to fetch the data either from remote or local database
 *
 */

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class SchoolRepository @Inject constructor(
    private val remoteDataSource: SchoolRemoteDataSource,
    private val localDataSource: SchoolLocalDataSource
) {
    /**
     *  This method demonstrates how we store the school data when the internet is available
     *  and render the school data when the internet is not available
     */
     suspend fun loadAllSchools(isNetworkAvailable: Boolean) : Flow<List<School>> {
        return localDataSource.getAllSchools()
            .flatMapMerge { schoolsFromDb ->
                if(schoolsFromDb.isEmpty() || isNetworkAvailable) {
                    return@flatMapMerge remoteDataSource.loadAllSchools()
                        .map { schoolList ->
                            val newSchoolList = mutableListOf<School>()
                            schoolList.forEach {
                                newSchoolList.add(School(
                                    it.dbn,
                                    it.school_name,
                                    it.boro,
                                    it.overview_paragraph,
                                    it.phone_number,
                                    it.website,
                                    it.total_students,
                                    it.extracurricular_activities,
                                    it.primary_address_line_1,
                                    it.city,
                                    it.state_code,
                                    it.zip,
                                    it.details
                                ))
                            }
                            schoolList
                        }
                        .flatMapConcat { schoolsToInsertInDb ->
                              localDataSource.deleteAllSchools()
                              localDataSource.saveAllSchools(schoolsToInsertInDb)
                                  .flatMapConcat {
                                      flow {
                                          emit(schoolsToInsertInDb)
                                      }
                                  }
                        }
                } else {
                    return@flatMapMerge flow {
                        emit(schoolsFromDb)
                    }
                }
            }
    }

    fun loadSchoolsByBoro(boro: String): Flow<List<School>> {
        return remoteDataSource.loadSchoolsByBoro(boro)
    }

    fun loadSchoolDetails(school: School): Flow<School> {
        return remoteDataSource.loadSchoolDetails(school)
    }
}