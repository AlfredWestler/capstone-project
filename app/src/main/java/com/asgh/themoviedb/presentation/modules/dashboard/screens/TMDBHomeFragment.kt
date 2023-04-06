package com.asgh.themoviedb.presentation.modules.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.asgh.themoviedb.databinding.TmdbHomeFragmentBinding
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardRxViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.MovieDataItem
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.TMDBMoviesAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBHomeFragment : Fragment() {

    private lateinit var binding: TmdbHomeFragmentBinding
    private val vm: TMDBDashboardRxViewModel by activityViewModels()

    private val topAdapter by lazy { TMDBMoviesAdapter() }
    private val nowPlayingAdapter by lazy { TMDBMoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TmdbHomeFragmentBinding.inflate(inflater, container, false)
        vm.apply {
            setToolbarTitle("")
            getAll()
        }
        setAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            topRatedState.observe(viewLifecycleOwner) {
                it.onEach(
                    onFailure = { failure ->
                        topAdapter.submitList(listOf(MovieDataItem.Failure(failure.message)))
                    },
                    onLoading = {
                        val loadItem = (0..10).toList()
                        topAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                    },
                    onSuccess = { topList ->
                        topAdapter.submitList(topList.map { mapper -> MovieDataItem.Success(mapper) })
                    }
                )
            }

            nowPlayingState.observe(viewLifecycleOwner) {
                it.onEach(
                    onFailure = { failure ->
                        nowPlayingAdapter.submitList(listOf(MovieDataItem.Failure(failure.message)))
                    },
                    onLoading = {
                        val loadItem = (0..10).toList()
                        nowPlayingAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                    },
                    onSuccess = { nowPlayingList ->
                        Picasso.get()
                            .load(vm.randomCover(nowPlayingList))
                            .fit().into(binding.mainCover)
                        nowPlayingAdapter.submitList(nowPlayingList.map { mapper -> MovieDataItem.Success(mapper) })
                    }
                )
            }
        }
    }

    private fun setAdapter() {
        binding.topMoviesRecycler.adapter = topAdapter
        binding.currentMoviesRecycler.adapter = nowPlayingAdapter
    }
}