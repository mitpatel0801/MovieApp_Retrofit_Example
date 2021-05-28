package com.example.movieappretrofitexample.ui.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieappretrofitexample.R
import com.example.movieappretrofitexample.data.network.ErrorCode
import com.example.movieappretrofitexample.data.network.Status
import com.example.movieappretrofitexample.ui.adapters.MovieListAdapter
import com.example.movieappretrofitexample.ui.viewmodels.MovieListViewModel

class MovieListFragment : Fragment() {

    private lateinit var movieList: RecyclerView
    private lateinit var viewModel: MovieListViewModel
    private lateinit var statusError: TextView
    private lateinit var loadingStatus: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(view)

        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        with(movieList)
        {
            adapter = MovieListAdapter {
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                        it
                    )
                )
            }
        }

        viewModel.movies.observe(viewLifecycleOwner, {
            (movieList.adapter as MovieListAdapter).setMovieList(it)
            if (it.isEmpty()) {
                viewModel.fetchFromNetwork()
            }
        })

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = false
            when (it?.status) {
                Status.SUCCESS -> {

                    statusError.visibility = View.INVISIBLE
                    loadingStatus.visibility = View.INVISIBLE
                }
                Status.ERROR -> {

                    statusError.visibility = View.VISIBLE
                    loadingStatus.visibility = View.INVISIBLE
                    showErrorMessage(it.errorCode, it.message)
                }
                Status.LOADING -> {
                    statusError.visibility = View.INVISIBLE
                    loadingStatus.visibility = View.VISIBLE
                }
            }

        })

    }


    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> statusError.text = "No Data returned from server. Please try again"
            ErrorCode.UNKNOWN_ERROR -> statusError.text = "Unknown error. $message"
            ErrorCode.NETWORK_ERROR -> statusError.text =
                "Error fetching data. Please Check network "
        }
    }

    private fun bind(view: View) {
        movieList = view.findViewById(R.id.movie_list)
        statusError = view.findViewById(R.id.status_erroe)
        loadingStatus = view.findViewById(R.id.loading_status)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
    }


}