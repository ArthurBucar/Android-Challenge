package com.android_tech_challenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android_tech_challenge.data.AptoideApi
import com.android_tech_challenge.data.AppRepositoryImpl
import com.android_tech_challenge.domain.AppRepository
import com.android_tech_challenge.domain.GetAppsUseCase
import com.android_tech_challenge.domain.GetFavoritesUseCase
import com.android_tech_challenge.domain.ToggleFavoriteUseCase
import com.android_tech_challenge.data.cache.AppDatabase
import com.android_tech_challenge.data.cache.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAptoideApi(): AptoideApi = Retrofit.Builder()
        .baseUrl("http://ws2.aptoide.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AptoideApi::class.java)

    @Provides
    @Singleton
    fun provideAppRepository(
        api: AptoideApi,
        dao: AppDao
    ): AppRepository = AppRepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideGetAppsUseCase(repository: AppRepository): GetAppsUseCase = GetAppsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(repository: AppRepository): GetFavoritesUseCase = GetFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repository: AppRepository): ToggleFavoriteUseCase = ToggleFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "apps.db"
    ).build()

    @Provides
    fun provideAppDao(db: AppDatabase): AppDao = db.appDao()
} 