package com.example.anilist.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.anilist.HomeFragmentDirections
import com.example.anilist.R
import com.example.anilist.databinding.RankingItemBinding
import com.example.anilist.pojo.Anime
import com.example.anilist.ui.fragment.RankingViewAllFragmentDirections
import com.example.anilist.ui.fragment.ViewAllFragmentDirections
import kotlin.random.Random

class AllTimeAniAdapter: RecyclerView.Adapter<AllTimeAniAdapter.AllTimeAniViewHolder>(){
    private var aniList = ArrayList<Anime>()
    private val colorList = ArrayList<Int>().addAll(listOf(Color.RED, Color.GRAY, Color.GREEN, Color.BLUE, Color.YELLOW))
    private var layout = "Home"

    @SuppressLint("NotifyDataSetChanged")
    fun setAniList(aniList : List<Anime>){
        this.aniList.addAll(aniList)
        notifyDataSetChanged()
    }

    fun setLayout(layout: String){
        this.layout = layout
    }

    inner class AllTimeAniViewHolder(var binding : RankingItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTimeAniAdapter.AllTimeAniViewHolder {
        return AllTimeAniViewHolder(
            com.example.anilist.databinding.RankingItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return aniList.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: AllTimeAniAdapter.AllTimeAniViewHolder, position: Int) {
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
            } else {
                val action = RankingViewAllFragmentDirections
                    .actionRankingViewAllFragmentToDetailFragment(ani.mal_id)
                holder.itemView.findNavController().navigate(action)
            }
        }
        holder.binding.tvRanking.text = holder.itemView.context.getString(R.string.ranking_id, position + 1)
        var drawable = holder.itemView.context.getDrawable(R.drawable.badge_background)
        drawable = DrawableCompat.wrap(drawable!!)
        val color = Random.nextInt()
        DrawableCompat.setTint(drawable, color)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_ATOP)
    }
}