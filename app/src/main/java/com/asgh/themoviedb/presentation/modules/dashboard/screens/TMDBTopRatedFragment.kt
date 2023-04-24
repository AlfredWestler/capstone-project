package com.asgh.themoviedb.presentation.modules.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.R
import com.asgh.themoviedb.databinding.TmdbTopRatedFragmentBinding
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardRxViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardViewModel
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.MovieDataItem
import com.asgh.themoviedb.presentation.modules.dashboard.adapters.TMDBMoviesAdapter
import com.squareup.picasso.Picasso

class TMDBTopRatedFragment : Fragment() {

    private lateinit var binding: TmdbTopRatedFragmentBinding
//    private val vm: TMDBDashboardRxViewModel by activityViewModels()
    private val vm: TMDBDashboardViewModel by activityViewModels()

    private val topAdapter by lazy { TMDBMoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TmdbTopRatedFragmentBinding.inflate(inflater, container, false)
        vm.setToolbarTitle(requireContext().resources.getString(R.string.top_rated))
        binding.nextTopRecycler.adapter = topAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            topRatedState.observe(viewLifecycleOwner) { state ->
                state.apply {
                    onFailure {
                        topAdapter.submitList(listOf(MovieDataItem.Failure(it.message)))
                    }
                    onLoading {
                        val loadItem = (0..10).toList()
                        topAdapter.submitList(loadItem.map { MovieDataItem.Loading })
                    }
                    onSuccess { topList ->
                        binding.apply {
                            Picasso.get()
                                .load(topList[0].posterPath)
                                .fit().into(binding.topOneImage)
                            Picasso.get()
                                .load(topList[1].posterPath)
                                .fit().into(binding.topTwoImage)
                            Picasso.get()
                                .load(topList[2].posterPath)
                                .fit().into(binding.topThreeImage)
                        }
                        val newTopList = topList.subList(3, topList.size)
                        topAdapter.submitList(
                            newTopList.map { mapper -> MovieDataItem.Success(mapper) }
                        )
                    }
                }
            }
        }
    }
}