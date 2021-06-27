package com.example.submission2.tvshow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission2.App
import com.example.submission2.databinding.TvShowFragmentBinding
import javax.inject.Inject

class TvShowFragment : Fragment() {
    @Inject
    lateinit var viewModel: TvShowViewModel
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowFragmentBinding: TvShowFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowFragmentBinding = TvShowFragmentBinding.inflate(inflater, container, false)
        return tvShowFragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as App).movieComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowAdapter = TvShowAdapter()
        getTvShow()
        with(tvShowFragmentBinding.rvTvShow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        getTvShow()
                    }
                }
            })
        }
    }

    fun getTvShow() {
        if(tvShowFragmentBinding.progressBar.visibility != View.VISIBLE) {
            tvShowFragmentBinding.progressBar.visibility = View.VISIBLE
            viewModel.getListTvShow().observe(viewLifecycleOwner, {
                tvShowAdapter.setTvShow(it)
                tvShowAdapter.notifyDataSetChanged()
                tvShowFragmentBinding.progressBar.visibility = View.GONE
            })
        }
    }
}