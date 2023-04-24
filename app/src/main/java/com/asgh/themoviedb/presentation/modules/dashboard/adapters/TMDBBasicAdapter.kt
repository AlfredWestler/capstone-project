package com.asgh.themoviedb.presentation.modules.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.asgh.themoviedb.BuildConfig
import com.asgh.themoviedb.databinding.RecyclerItemBinding
import com.asgh.themoviedb.domain.model.TMDBMovie
import com.squareup.picasso.Picasso

class MoviesOrShowsAdapter(
    private var onClick: (itemSelected: TMDBMovie) -> Unit = {}
): ListAdapter<TMDBMovie, MoviesOrShowsAdapter.MoviesOrShowsViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<TMDBMovie>() {
            override fun areItemsTheSame(oldItem: TMDBMovie, newItem: TMDBMovie): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TMDBMovie, newItem: TMDBMovie): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesOrShowsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RecyclerItemBinding.inflate(inflater, parent, false)
        return MoviesOrShowsViewHolder(view)
    }


    override fun onBindViewHolder(holder: MoviesOrShowsViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClick(getItem(position))
        }
    }

    class MoviesOrShowsViewHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(result: TMDBMovie){
            val imageUrl = result.posterPath
            Picasso.get().load(imageUrl)
                .fit()
                .into(binding.posterImage)
        }
    }
}