package com.asgh.themoviedb.presentation.modules.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asgh.themoviedb.databinding.RecyclerFailureBinding
import com.asgh.themoviedb.databinding.RecyclerItemBinding
import com.asgh.themoviedb.databinding.RecyclerLoadingBinding
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.squareup.picasso.Picasso

class TMDBMoviesAdapter(
    val onClick: (itemSelected: TMDBMovie) -> Unit = {}
): ListAdapter<MovieDataItem, TMDBMoviesAdapter.TMDBMoviesViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<MovieDataItem>() {

            override fun areItemsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean =
                oldItem.onSuccess()?.id == newItem.onSuccess()?.id

            override fun areContentsTheSame(oldItem: MovieDataItem, newItem: MovieDataItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TMDBMoviesViewHolder =
        TMDBMoviesViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: TMDBMoviesViewHolder, position: Int) {
        when(val item = getItem(position)) {
            is MovieDataItem.Success -> {
                (holder as SuccessViewHolder).bind(item)
                holder.itemView.setOnClickListener { onClick(item.movie) }
            }
            MovieDataItem.Loading -> (holder as LoadingViewHolder)
            is MovieDataItem.Failure -> (holder as FailureViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MovieDataItem.Success -> MovieDataType.SUCCESS
        MovieDataItem.Loading -> MovieDataType.LOADING
        is MovieDataItem.Failure -> MovieDataType.FAILURE
    }.ordinal

    abstract class TMDBMoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun create(parent: ViewGroup, viewType: Int): TMDBMoviesViewHolder {
                val type = MovieDataType.values()[viewType]
                val inflater = LayoutInflater.from(parent.context)
                return when(type) {
                    MovieDataType.SUCCESS -> {
                        val view = RecyclerItemBinding.inflate(inflater, parent, false)
                        SuccessViewHolder(view)
                    }
                    MovieDataType.LOADING -> {
                        val view = RecyclerLoadingBinding.inflate(inflater, parent, false)
                        LoadingViewHolder(view)
                    }
                    MovieDataType.FAILURE -> {
                        val view = RecyclerFailureBinding.inflate(inflater, parent, false)
                        FailureViewHolder(view)
                    }
                }
            }
        }
    }

    class SuccessViewHolder(
        private val binding: RecyclerItemBinding
    ) : TMDBMoviesViewHolder(binding.root) {

        fun bind(item: MovieDataItem.Success) {
            val imageUrl = item.movie.posterPath
            Picasso.get().load(imageUrl)
                .fit()
                .into(binding.posterImage)
        }
    }

    class LoadingViewHolder(
        binding: RecyclerLoadingBinding
    ) : TMDBMoviesViewHolder(binding.root)

    class FailureViewHolder(
        private val binding: RecyclerFailureBinding
    ) : TMDBMoviesViewHolder(binding.root) {

        fun bind(item: MovieDataItem.Failure) {
            binding.failMessageText.text = item.message
        }
    }
}

sealed class MovieDataItem {
    class Success(val movie: TMDBMovie): MovieDataItem()
    object Loading: MovieDataItem()
    class Failure(val message: String): MovieDataItem()

    fun onSuccess(): TMDBMovie? =
        when(this) {
            is Success -> movie
            else -> null
        }
}

enum class MovieDataType {
    SUCCESS,
    LOADING,
    FAILURE
}