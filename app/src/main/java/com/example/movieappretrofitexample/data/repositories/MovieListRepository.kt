package com.example.movieappretrofitexample.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.movieappretrofitexample.data.db.Movie
import com.example.movieappretrofitexample.data.db.MovieDatabase
import com.example.movieappretrofitexample.data.network.ErrorCode
import com.example.movieappretrofitexample.data.network.LoadingStatus
import com.example.movieappretrofitexample.data.network.TmdbService
import java.net.UnknownHostException



class MovieListRepository(context: Application) {

    private val movieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy { TmdbService.getInstance() }

    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun fetchFromNetwork(): LoadingStatus =
        try {
            val response = tmdbService.getMovies()
            if (response.isSuccessful) {
                val movieList = response.body()
                movieList?.let {
                    movieListDao.insertMovies(it.results)
                }
                LoadingStatus.success()
            } else {
                LoadingStatus.error(ErrorCode.NO_DATA)
            }
        } catch (ex: UnknownHostException) {
            LoadingStatus.error(ErrorCode.NETWORK_ERROR)
        } catch (ex: Exception) {
            LoadingStatus.error(ErrorCode.UNKNOWN_ERROR, ex.message)
        }

    suspend fun deleteAllData() {
        movieListDao.deleteAll()
    }


}
