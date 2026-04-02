package io.mmaltsev.vkeducation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mmaltsev.vkeducation.data.applist.AppListRepositoryImpl
import io.mmaltsev.vkeducation.domain.repository.AppListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppListRepository(
        impl: AppListRepositoryImpl
    ): AppListRepository
}