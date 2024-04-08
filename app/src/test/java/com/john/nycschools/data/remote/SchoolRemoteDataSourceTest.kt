package com.john.nycschools.data.remote

import com.john.nycschools.models.School
import com.john.nycschools.models.SchoolDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SchoolRemoteDataSourceTest {

    private companion object {
        const val boro = "K"
        val schools = listOf(
            School()
        )
        val knownSchoolDetails = listOf(SchoolDetails())
        val knownSchool = School(
            dbn = "test_dbn",
            details = knownSchoolDetails[0]
        )
        val knownSchoolWithNullDetails = School(
            dbn = "test_dbn",
            details = null
        )
    }

    @Mock
    lateinit var service: NycSchoolService

    @Test
    fun testLoadAllSchools() = runBlocking {
        val dataSource = SchoolRemoteDataSource(service)
        `when`(service.getAllSchools()).thenReturn(schools)

        dataSource.loadAllSchools()
            .collect {
                Assert.assertEquals(schools, it)
            }
    }

    @Test
    fun testSchoolsByBoro() = runBlocking {
        val dataSource = SchoolRemoteDataSource(service)
        `when`(service.getSchoolsByBoro(boro)).thenReturn(schools)
        dataSource.loadSchoolsByBoro(boro)
            .collect {
                Assert.assertEquals(schools, it)
            }
    }

    @Test
    fun testLoadSchoolDetails() = runBlocking {
        val dataSource = SchoolRemoteDataSource(service)
        `when`(service.getSchoolDetails(knownSchool.dbn)).thenReturn(knownSchoolDetails)
        dataSource.loadSchoolDetails(knownSchool)
            .collect {
                Assert.assertEquals(knownSchool, it)
            }
    }

    @Test
    fun testLoadSchoolDetailsWhenDetailsAreNull() = runBlocking {
        val dataSource = SchoolRemoteDataSource(service)
        `when`(service.getSchoolDetails(knownSchoolWithNullDetails.dbn)).thenReturn(null)
        dataSource.loadSchoolDetails(knownSchoolWithNullDetails)
            .collect {
                Assert.assertEquals(knownSchoolWithNullDetails, it)
            }
    }
}