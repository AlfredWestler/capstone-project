package com.asgh.themoviedb.presentation.modules.dashboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.asgh.themoviedb.R
import com.asgh.themoviedb.databinding.TmdbLatestFragmentBinding
import com.asgh.themoviedb.presentation.modules.dashboard.TMDBDashboardViewModel
import com.squareup.picasso.Picasso

class TMDBLatestFragment : Fragment() {

    private lateinit var binding: TmdbLatestFragmentBinding
//    private val vm: TMDBDashboardRxViewModel by activityViewModels()
    private val vm: TMDBDashboardViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TmdbLatestFragmentBinding.inflate(inflater, container, false)
        vm.setToolbarTitle(requireContext().resources.getString(R.string.latest))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            lifecycleScope.launchWhenCreated {
                latestState.collect { result ->
                    result.apply {
                        onSuccess {
                            binding.apply {
                                Picasso.get()
                                    .load(it.poster_path)
                                    .fit().into(binding.mainCover)
                                tagsText.text = it.tags()
                                titleText.text = "${it.title} (${it.original_title})"
                                overviewText.text = it.overview
                            }
                        }
                    }
                }
            }
        }
        binding.closeInfoBtn.setOnClickListener {
            binding.infoCard.visibility = View.GONE
        }
    }
}