package com.example.themoviedemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedemo.models.entity.Movie
import com.example.themoviedemo.models.entity.Person
import com.example.themoviedemo.models.entity.Tv
import com.example.themoviedemo.utils.*


@Database(entities = [(Movie::class), (Tv::class), (Person::class)], version = 3, exportSchema = false)
@TypeConverters(value = [(StringListConverter::class), (IntegerListConverter::class),
    (KeywordListConverter::class), (VideoListConverter::class), (ReviewListConverter::class)])
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun peopleDao(): PeopleDao
}
