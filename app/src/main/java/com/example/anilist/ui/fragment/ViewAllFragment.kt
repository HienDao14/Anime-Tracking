package com.example.anilist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.anilist.adapter.AniAdapter
import com.example.anilist.databinding.FragmentTrendingViewAllBinding
import com.example.anilist.viewModel.HomeViewModel


class ViewAllFragment : Fragment() {

    private val args : ViewAllFragmentArgs by navArgs()
    private var type: String = ""
    private lateinit var binding: FragmentTrendingViewAllBinding
    private lateinit var adapter: AniAdapter
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = args.type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrendingViewAllBinding.inflate(inflater, container, false)
        adapter = AniAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        when(type){
            "Trending"-> trending()
            "Season" -> season()
            "Popular" -> popular()
        }
    }

    private fun popular() {
        adapter.setLayout("Popular")
        viewModel.getPopularAllAnime()
        observePopularAllAnime()
    }

    private fun observePopularAllAnime() {
        viewModel.popularAllAnimeLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setAniList(it)
        })
    }

    private fun prepareRecyclerView() {
        binding.rvTrending.adapter = adapter
    }

    private fun season() {
        adapter.setLayout("Season")
        viewModel.getSeasonAnime()
        observeSeasonAnime()
    }

    private fun observeSeasonAnime() {
        viewModel.seasonAnimeLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setAniList(it)
        })
    }

    private fun trending() {
        adapter.setLayout("Trending")
        viewModel.getTrendingAnime()
        observeTrendingAnimeLiveData()
    }

    private fun observeTrendingAnimeLiveData() {
        viewModel.trendingAnimesLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setAniList(it)
        })
    }

}