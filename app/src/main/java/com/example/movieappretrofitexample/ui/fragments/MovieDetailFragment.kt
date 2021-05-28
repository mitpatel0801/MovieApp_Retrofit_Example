package com.example.movieappretrofitexample.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieappretrofitexample.R
import com.example.movieappretrofitexample.data.db.Movie
import com.example.movieappretrofitexample.data.network.TmdbService
import com.example.movieappretrofitexample.readableFormate
import com.example.movieappretrofitexample.ui.viewmodels.MovieDetailViewModel
import com.example.movieappretrofitexample.ui.viewmodels.viewModelFactory.MovieDetailViewModelFactory

class MovieDetailFragment : Fragment() {

    private lateinit var movieTitle: TextView
    private lateinit var movieOverView: TextView
    private lateinit var movieDate: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieBackDrop: ImageView

    private var id: Long? = null
    private lateinit var viewModel: MovieDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView(view)

        id = MovieDetailFragmentArgs.fromBundle(requireArguments()).id

        viewModel = ViewModelProvider(
            this,
            MovieDetailViewModelFactory(id!!, requireActivity().application)
        )[MovieDetailViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner, {
            setMovie(it)
        })
    }

    private fun bindView(view: View) {
        movieTitle = view.findViewById(R.id.movie_title)
        movieOverView = view.findViewById(R.id.movie_overview)
        movieDate = view.findViewById(R.id.movie_date)
        moviePoster = view.findViewById(R.id.movie_poster)
        movieBackDrop = view.findViewById(R.id.movie_backdrop)
    }

    private fun setMovie(movie: Movie?) {


        movie?.run {
            movieDate.text = this.releaseDate.readableFormate()
            movieOverView.text = this.overview
            movieTitle.text = this.title

            Glide.with(requireActivity())
                .load(TmdbService.POSTER_PATH_URL + movie.posterPath)
                .into(moviePoster)

            Glide.with(requireActivity())
                .load(TmdbService.BACKDROP_BASE_URL + movie.backdropPath)
                .into(movieBackDrop)
        }


    }

}