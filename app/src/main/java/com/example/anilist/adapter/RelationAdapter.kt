package com.example.anilist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anilist.databinding.RelationItemBinding
import com.example.anilist.pojo.RelationWithImage
import com.example.anilist.pojo.Relations

class RelationAdapter: RecyclerView.Adapter<RelationAdapter.RelationViewHolder>() {

    private var relations = ArrayList<RelationWithImage>()
    @SuppressLint("NotifyDataSetChanged")
    fun setUpRelation(relation: ArrayList<RelationWithImage>){
        this.relations = relation
        notifyDataSetChanged()
    }


    inner class RelationViewHolder(val binding : RelationItemBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelationViewHolder {
        return RelationViewHolder(
            RelationItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RelationViewHolder, position: Int) {
        val relation = relations[position]
        Glide.with(holder.itemView).load(relation.imageUrl).into(holder.binding.ivRelation)
        holder.binding.tvSource.text = relation.relation
        holder.binding.tvName.text = relation.entry.name
        holder.binding.tvType.text = relation.entry.type
    }

    override fun getItemCount(): Int {
        return relations.size
    }
}