package com.example.submission2.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission2.App
import com.example.submission2.databinding.MovieFragmentBinding
import javax.inject.Inject

class MovieFragment : Fragment() {
    @Inject
    lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieFragmentBinding: MovieFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieFragmentBinding = MovieFragmentBinding.inflate(inflater, container, false)
        return movieFragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as App).movieComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter()
        getMovie()
        with(movieFragmentBinding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        getMovie()
                    }
                }
            })
        }
    }

    fun getMovie() {
        if(movieFragmentBinding.progressBar.visibility != View.VISIBLE) {
            movieFragmentBinding.progressBar.visibility = View.VISIBLE
            viewModel.getListMovie().observe(viewLifecycleOwner, {
                movieAdapter.setMovie(it)
                movieAdapter.notifyDataSetChanged()
                movieFragmentBinding.progressBar.visibility = View.GONE
            })
        }
    }
}