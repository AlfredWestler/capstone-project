package com.asgh.themoviedb.presentation.modules.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.R
import com.asgh.themoviedb.databinding.TmdbNowPlayingFragmentBinding
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardRxViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.MovieDataItem
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.TMDBMoviesAdapter
import com.squareup.picasso.Picasso

class TMDBNowPlayingFragment : Fragment() {

    private lateinit var binding: TmdbNowPlayingFragmentBinding
//    private val vm: TMDBDashboardRxViewModel by activityViewModels()
    private val vm: TMDBDashboardViewModel by activityViewModels()

    private val nowPlayingAdapter by lazy {
        TMDBMoviesAdapter {
            val url = it.backdropPath
            updateTheatreImage(url)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TmdbNowPlayingFragmentBinding.inflate(inflater, container, false)
        vm.setToolbarTitle(requireContext().resources.getString(R.string.now_playing))
        binding.theatreRecycler.adapter = nowPlayingAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            nowPlayingState.observe(viewLifecycleOwner) {
                it.apply {
                    onFailure { failure ->
                        nowPlayingAdapter.submitList(listOf(MovieDataItem.Failure(failure.message)))
                    }
                    onLoading {
                        val loadItem = (0..10).toList()
                        nowPlayingAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                    }
                    onSuccess { nowPlayingList ->
                        Picasso.get()
                            .load(vm.randomBackdrop(nowPlayingList))
                            .fit().into(binding.theatreImage)
                        nowPlayingAdapter.submitList(nowPlayingList.map { mapper -> MovieDataItem.Success(mapper) })
                    }
                }
            }
        }
    }

    private fun updateTheatreImage(url: String) {
        Picasso.get()
            .load(url)
            .fit().into(binding.theatreImage)
    }
}