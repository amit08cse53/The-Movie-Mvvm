package com.example.themoviedemo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.themoviedemo.models.entity.Movie



@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)

    @Query("SELECT * FROM MOVIE WHERE id = :id_")
    fun getMovie(id_: Int): Movie

    @Query("SELECT * FROM Movie WHERE page = :page_")
    fun getMovieList(page_: Int): LiveData<List<Movie>>
}
