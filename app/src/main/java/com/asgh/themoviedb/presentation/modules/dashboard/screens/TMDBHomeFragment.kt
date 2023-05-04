package com.asgh.themoviedb.presentation.modules.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.asgh.themoviedb.commons.converters.toJson
import com.asgh.themoviedb.databinding.TmdbHomeFragmentBinding
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.MovieDataItem
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.TMDBMoviesAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TMDBHomeFragment : Fragment() {

    private lateinit var binding: TmdbHomeFragmentBinding
//    private val vm: TMDBDashboardRxViewModel by activityViewModels()
    private val vm: TMDBDashboardViewModel by activityViewModels()

    private val topAdapter by lazy {
        TMDBMoviesAdapter { navigateToDetail(it) }
    }
    private val nowPlayingAdapter by lazy {
        TMDBMoviesAdapter { navigateToDetail(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TmdbHomeFragmentBinding.inflate(inflater, container, false)
        vm.apply { setToolbarTitle("") }
        setAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            lifecycleScope.launchWhenCreated {
                topRatedState.collect {
                    it.apply {
                        onFailure { failure ->
                            topAdapter.submitList(listOf(MovieDataItem.Failure(failure.message)))
                        }
                        onLoading {
                            val loadItem = (0..10).toList()
                            topAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                        }
                        onSuccess { topList ->
                            topAdapter.submitList(topList.map { mapper -> MovieDataItem.Success(mapper) })
                        }
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                nowPlayingState.collect {
                    it.apply {
                        onFailure { failure ->
                            nowPlayingAdapter.submitList(listOf(MovieDataItem.Failure(failure.message)))
                        }
                        onLoading {
                            val loadItem = (0..10).toList()
                            nowPlayingAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                        }
                        onSuccess { nowPlayingList ->
                            val randomData = vm.randomItem(nowPlayingList)
                            Picasso.get()
                                .load(randomData.posterPath)
                                .fit().into(binding.mainCover)
                            binding.mainCover.setOnClickListener {
                                navigateToDetail(randomData)
                            }
                            nowPlayingAdapter.submitList(nowPlayingList.map { mapper ->
                                MovieDataItem.Success(
                                    mapper
                                )
                            })
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        binding.topMoviesRecycler.adapter = topAdapter
        binding.currentMoviesRecycler.adapter = nowPlayingAdapter
    }

    private fun navigateToDetail(movie: TMDBMovie) {
        val action = TMDBHomeFragmentDirections.homeToDetailAction(/*toJson(movie)*/movie.id.toString())
        findNavController().navigate(action)
    }
}