package com.example.movieappretrofitexample.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movieappretrofitexample.data.db.Movie
import com.example.movieappretrofitexample.data.db.MovieDatabase

class MovieDetailRepository(context: Application) {

    private val movieData = MovieDatabase.getDatabase(context).movieDetailDao()

    fun getMovie(id: Long): LiveData<Movie> {
        return movieData.getMovie(id)
    }
}