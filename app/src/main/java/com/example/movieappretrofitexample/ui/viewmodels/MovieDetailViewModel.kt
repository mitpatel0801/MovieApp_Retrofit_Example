package com.example.movieappretrofitexample.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieappretrofitexample.data.db.Movie
import com.example.movieappretrofitexample.data.repositories.MovieDetailRepository

class MovieDetailViewModel(id:Long,application: Application):ViewModel() {
    private val repo = MovieDetailRepository(application)
    val movie = repo.getMovie(id)
}