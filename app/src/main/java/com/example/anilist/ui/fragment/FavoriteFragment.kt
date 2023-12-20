package com.example.anilist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.anilist.R
import com.example.anilist.adapter.FavoriteAdapter
import com.example.anilist.database.FavoriteAnimeApplication
import com.example.anilist.databinding.FragmentFavoriteBinding
import com.example.anilist.viewModel.AnimeViewModelFactory
import com.example.anilist.viewModel.FavoriteViewModel


class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by activityViewModels {
        AnimeViewModelFactory(
            (activity?.application as FavoriteAnimeApplication).database.animeDao()
        )
    }

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoriteAdapter{
            val action = FavoriteFragmentDirections
                .actionFavoriteFragmentToDetailFragment(it.malId)
            this.findNavController().navigate(action)
        }
        binding.rvFavorite.adapter = adapter
        viewModel.allFavoriteAnime.observe(this.viewLifecycleOwner){anime ->
            anime.let{
                adapter.submitList(it)
            }
        }
    }
}