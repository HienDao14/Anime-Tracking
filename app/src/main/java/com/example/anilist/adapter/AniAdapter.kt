package com.example.anilist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.anilist.HomeFragmentDirections
import com.example.anilist.SearchFragmentDirections
import com.example.anilist.databinding.RecyclerViewItemBinding
import com.example.anilist.pojo.Anime
import com.example.anilist.ui.fragment.ViewAllFragmentDirections

class AniAdapter : RecyclerView.Adapter<AniAdapter.AniViewHolder>() {

    private var aniList = ArrayList<Anime>()
    private var layout = "Home"

    @SuppressLint("NotifyDataSetChanged")
    fun setAniList(aniList : List<Anime>){
        this.aniList = aniList as ArrayList<Anime>
        notifyDataSetChanged()
    }

    fun setLayout(s : String){
        this.layout = s
    }

    inner class AniViewHolder(val binding : RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AniViewHolder {
        return AniViewHolder(RecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return aniList.size
    }

    override fun onBindViewHolder(holder: AniViewHolder, position: Int) {
        val ani = aniList[position]
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
        Glide.with(holder.itemView)
            .load(ani.images.jpg.image_url)
            .apply(requestOptions)
            .into(holder.binding.ivImage)
        holder.binding.tvTitle.text = ani.title

        holder.itemView.setOnClickListener {
            if(layout == "Home"){
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(ani.mal_id)
                holder.itemView.findNavController().navigate(action)
            } else if(layout == "Search"){
                val action = SearchFragmentDirections
                    .actionSearchFragmentToDetailFragment(ani.mal_id)
                holder.itemView.findNavController().navigate(action)
            }
            else {
                val action = ViewAllFragmentDirections
                    .actionTrendingViewAllToDetailFragment(ani.mal_id)
                holder.itemView.findNavController().navigate(action)
            }
        }
    }
}