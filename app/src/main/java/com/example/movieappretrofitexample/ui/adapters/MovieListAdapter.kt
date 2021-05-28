package com.example.movieappretrofitexample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappretrofitexample.R
import com.example.movieappretrofitexample.data.db.Movie
import com.example.movieappretrofitexample.data.network.TmdbMovieList
import com.example.movieappretrofitexample.data.network.TmdbService


class MovieListAdapter(val listener: (id: Long) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var movieList: MutableList<Movie> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.findViewById<TextView>(R.id.movie_title)
        private val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)


        init {
          itemView.setOnClickListener {
             listener(movieList[absoluteAdapterPosition].id)
          }
        }

        fun bind(movie: Movie) {
            with(movie)
            {
                Glide.with(itemView)
                    .load(TmdbService.POSTER_PATH_URL + movie.posterPath)
                    // .error(R.drawable.poster_placeholder)
                    .into(moviePoster)
                titleTextView.text = title
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }


    override fun getItemCount(): Int = movieList.size


    fun setMovieList(newMovieList: List<Movie>) {
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }
}