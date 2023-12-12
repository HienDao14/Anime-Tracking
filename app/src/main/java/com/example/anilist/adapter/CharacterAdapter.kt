package com.example.anilist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anilist.databinding.CharacterItemBinding
import com.example.anilist.pojo.Character
import com.example.anilist.pojo.Data

class CharacterAdapter(): RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private var listCharacter = ArrayList<Data>()
    private var isEnglish : Int = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setListCharacter(list : List<Data>){
        this.listCharacter.clear()
        this.listCharacter.addAll(list)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIsEnglish(isEnglish : Int){
        this.isEnglish = isEnglish
    }

    inner class CharacterViewHolder(var binding : CharacterItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = listCharacter[position]
        Glide.with(holder.itemView).load(character.character.images.jpg.image_url).into(holder.binding.ivCharacter)
        holder.binding.tvCharacterName.text = character.character.name
        holder.binding.tvRole.text = character.role

        Glide.with(holder.itemView).load(character.voice_actors[isEnglish].person.images.jpg.image_url).into(holder.binding.ivActor)
        holder.binding.tvActorName.text = character.voice_actors[isEnglish].person.name
        holder.binding.tvLanguage.text = character.voice_actors[isEnglish].language
    }

    override fun getItemCount(): Int {
        return listCharacter.size
    }
}