package com.john.nycschools.data.local

import com.john.nycschools.models.School
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class SchoolLocalDataSourceTest {

    private companion object {
        val schools = listOf(
            School()
        )
    }

    @Mock
    lateinit var schoolDao: SchoolDao

    @Mock
    lateinit var schoolDetailsDao: SchoolDetailsDao

    @Test
    fun testGetAllSchools() = runBlocking {
        val dataSource = SchoolLocalDataSource(
            schoolDao,
            schoolDetailsDao
        )

        `when`(schoolDao.getAllSchools()).thenReturn(schools)
        var schoolsReturned = emptyList<School>()

        dataSource.getAllSchools()
            .collect {
                schoolsReturned = it
            }

        Assert.assertEquals(
            schools,
            schoolsReturned
        )
    }


    @Test
    fun testSaveAllSchools() = runBlocking {
        val dataSource = SchoolLocalDataSource(
            schoolDao,
            schoolDetailsDao
        )

        dataSource.saveAllSchools(schools)
            .collect {
                Assert.assertEquals(
                    Unit,
                        it
                    )
            }
    }

}