package com.example.movieappretrofitexample.data.network

import com.example.movieappretrofitexample.BuildConfig
import com.google.gson.GsonBuilder

import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Response


interface TmdbService {


    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_PATH_URL = "https://image.tmdb.org/t/p/w200"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w300"
        private val retrofitService by lazy {

            val interceptor = Interceptor { chain ->
                val request = chain.request()
                val url = request.url().newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                    .build()

                val newRequest = request.newBuilder()
                    .url(url)
                    .build()

                chain.proceed(newRequest)
            }

            val httpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TmdbService::class.java)

        }

        fun getInstance(): TmdbService {
            return retrofitService
        }


    }

    @GET(
        "discover/movie?api_key=c1719991b91b59e4a1675ca0bb1170c2&sort_by=popularity.desc" +
                "&certification_country=US&include_adult=true&page=1&vote_count.gte=100&with_original_language=en"
    )
    suspend fun getMovies(): Response<TmdbMovieList>


}