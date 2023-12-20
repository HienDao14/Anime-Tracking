package com.example.anilist.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.anilist.adapter.AllTimeAniAdapter
import com.example.anilist.databinding.FragmentRankingViewAllBinding
import com.example.anilist.pojo.Anime
import com.example.anilist.viewModel.HomeViewModel
import kotlinx.coroutines.launch


class RankingViewAllFragment : Fragment() {

    private lateinit var binding: FragmentRankingViewAllBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var rankingAdapter: AllTimeAniAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRankingViewAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        lifecycleScope.launch {
            binding.rankingLoading.visibility = View.VISIBLE
            viewModel.setUpRankingLoadingBar(binding.rankingLoading)
            for(i in 1..1){
                viewModel.getRankingAnime(i)
                viewModel.rankingAnimeLiveData.observe(viewLifecycleOwner, Observer {
                    rankingAdapter.setAniList(it)
                })
            }
        }
    }

    private fun setUpRecyclerView() {
        rankingAdapter = AllTimeAniAdapter()
        rankingAdapter.setLayout("Ranking")
        binding.rvRanking.apply {
            adapter = rankingAdapter
        }
    }
}