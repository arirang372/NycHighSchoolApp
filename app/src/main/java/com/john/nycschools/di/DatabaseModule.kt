package com.john.nycschools.di

import android.content.Context
import com.john.nycschools.data.local.SchoolDao
import com.john.nycschools.data.local.SchoolDatabase
import com.john.nycschools.data.local.SchoolDetailsDao
import com.john.nycschools.data.local.SchoolLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  A class that injects the dependencies related to local data source
 *
 */

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesSchoolDatabase(@ApplicationContext context: Context) =
        SchoolDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideSchoolDao(schoolDatabase: SchoolDatabase) = schoolDatabase.schoolDao()

    @Singleton
    @Provides
    fun provideSchoolDetailsDao(schoolDatabase: SchoolDatabase) = schoolDatabase.schoolDetailsDao()

    @Singleton
    @Provides
    fun providesLocalDataSource(schoolDao: SchoolDao, schoolDetailsDao: SchoolDetailsDao) =
        SchoolLocalDataSource(schoolDao, schoolDetailsDao)
}