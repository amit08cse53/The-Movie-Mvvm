package com.example.themoviedemo.di

import android.app.Application
import androidx.room.Room
import androidx.annotation.NonNull
import com.example.themoviedemo.room.AppDatabase
import com.example.themoviedemo.room.MovieDao
import com.example.themoviedemo.room.PeopleDao
import com.example.themoviedemo.room.TvDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "TheMovies.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideTvDao(@NonNull database: AppDatabase): TvDao {
        return database.tvDao()
    }

    @Provides
    @Singleton
    fun providePeopleDao(@NonNull database: AppDatabase): PeopleDao {
        return database.peopleDao()
    }
}
