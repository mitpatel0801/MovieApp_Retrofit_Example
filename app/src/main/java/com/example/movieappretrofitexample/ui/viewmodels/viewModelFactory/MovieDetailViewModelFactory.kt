package com.example.movieappretrofitexample.ui.viewmodels.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappretrofitexample.ui.viewmodels.MovieDetailViewModel
import java.lang.IllegalArgumentException

class MovieDetailViewModelFactory(private val id: Long, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieDetailViewModel(id, application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}