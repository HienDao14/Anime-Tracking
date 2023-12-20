package com.example.anilist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anilist.database.FavoriteAnime
import com.example.anilist.databinding.RecyclerViewItemBinding

class FavoriteAdapter(private val onItemClicked: (FavoriteAnime) -> Unit): ListAdapter<FavoriteAnime, FavoriteAdapter.FavoriteViewHolder>(DiffCallback) {
    class FavoriteViewHolder(private val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(anime: FavoriteAnime){
            Glide.with(itemView).load(anime.imageUrl).into(binding.ivImage)
            binding.tvTitle.text = anime.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    companion object{
        val DiffCallback = object: DiffUtil.ItemCallback<FavoriteAnime>(){
            override fun areItemsTheSame(oldItem: FavoriteAnime, newItem: FavoriteAnime): Boolean {
                return oldItem.malId == newItem.malId
            }

            override fun areContentsTheSame(
                oldItem: FavoriteAnime,
                newItem: FavoriteAnime
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}