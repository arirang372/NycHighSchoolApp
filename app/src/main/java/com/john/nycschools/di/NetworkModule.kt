package com.john.nycschools.di

import com.john.nycschools.data.remote.NycSchoolService
import com.john.nycschools.data.remote.SchoolRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  A class that injects the dependencies related to remote data source
 *
 */

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesNycSchoolService(): NycSchoolService = NycSchoolService.create()

    @Singleton
    @Provides
    fun providesRemoteDataSource(service: NycSchoolService): SchoolRemoteDataSource = SchoolRemoteDataSource(service)
}