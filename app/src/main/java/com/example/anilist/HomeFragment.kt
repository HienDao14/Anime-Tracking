package com.example.anilist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anilist.adapter.AllTimeAniAdapter
import com.example.anilist.adapter.AniAdapter
import com.example.anilist.databinding.FragmentHomeBinding
import com.example.anilist.pojo.Anime
import com.example.anilist.viewModel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private var trendingAnime = ArrayList<Anime>()
    private var seasonAnime = ArrayList<Anime>()
    private var popularAnime = ArrayList<Anime>()
    private var rankingAnime = ArrayList<Anime>()
    private lateinit var trendingAdapter : AniAdapter
    private lateinit var seasonAdapter: AniAdapter
    private lateinit var popularAllSeasonAdapter : AniAdapter
    private lateinit var rankingAdapter : AllTimeAniAdapter
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater);

        val types = resources.getStringArray(R.array.type)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_selected, types)
        arrayAdapter.setDropDownViewResource(R.layout.drop_down_item)
        binding.spinner.spinner.adapter = arrayAdapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareTrendingRecyclerView()
        homeViewModel.setUpTrendingLoadingBar(binding.loadingTrending)
        homeViewModel.getTrendingAnime()
        observeTrendingAnime()

        prepareSeasonRecyclerView()
        homeViewModel.setUpPopularLoadingBar(binding.loadingPopular)
        homeViewModel.getSeasonAnime()
        observeSeasonAnime()

        preparePopularAllRecyclerView()
        homeViewModel.setUpAllTimeLoadingBar(binding.allTimeLoading)
        homeViewModel.getPopularAllAnime()
        observePopularAllAnime()

        prepareRankingRecyclerView()
        homeViewModel.setUpRankingLoadingBar(binding.rankingLoading)

        CoroutineScope(Dispatchers.Main).launch{
            delay(1500L)
            homeViewModel.getRankingAnime(1)
            observeRankingAnime()
        }

        onViewAllClicked()
        onSpinnerChange()
        setOnClickSearch()
    }

    private fun onSpinnerChange() {
        binding.spinner.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    1 -> {
                        val searchBundle = bundleOf("searchText" to binding.searchField.text.toString())
                        this@HomeFragment.findNavController().navigate(R.id.action_homeFragment_to_searchFragment, searchBundle)
                    }
                    2 -> {
                        this@HomeFragment.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setOnClickSearch() {
        binding.searchField.setOnEditorActionListener { v, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val searchBundle = bundleOf("searchText" to binding.searchField.text.toString())
                this.findNavController().navigate(R.id.action_homeFragment_to_searchFragment, searchBundle)
                true
            } else{
                false
            }
        }
    }

    private fun onViewAllClicked() {
        binding.trendingViewAll.setOnClickListener {
            navigateToViewAllScreen("Trending")
        }
        binding.seasonViewAll.setOnClickListener {
            navigateToViewAllScreen("Season")
        }
        binding.allTimeViewAll.setOnClickListener {
            navigateToViewAllScreen("Popular")
        }
        binding.rankingViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_rankingViewAllFragment)
        }
    }

    private fun navigateToViewAllScreen(type: String){
        val action = HomeFragmentDirections
            .actionHomeFragmentToTrendingViewAll(type)
        findNavController().navigate(action)
    }

    private fun observeRankingAnime() {
        if(view != null){
            homeViewModel.rankingAnimeLiveData.observe(viewLifecycleOwner, Observer {
                var i = 0
                while(rankingAnime.size < 10){
                    rankingAnime.add(it[i])
                    i++
                }
                rankingAdapter.setAniList(rankingAnime)
            })
        }
    }

    private fun prepareRankingRecyclerView() {
        rankingAdapter = AllTimeAniAdapter()
        binding.rvRanking.apply{
            layoutManager = GridLayoutManager(context, 3)
            adapter = rankingAdapter
        }
    }

    private fun observePopularAllAnime() {
        val binding = binding
        homeViewModel.popularAllAnimeLiveData.observe(viewLifecycleOwner, Observer {
            var i = 0
            popularAnime.clear()
            while(popularAnime.size < 6){
                popularAnime.add(it[i])
                i++
            }
            popularAllSeasonAdapter.setAniList(popularAnime)
        })
    }

    private fun preparePopularAllRecyclerView() {
        popularAllSeasonAdapter = AniAdapter()
        binding.rvPopularAll.apply{
            layoutManager = GridLayoutManager(context, 3)
            adapter = popularAllSeasonAdapter
        }
    }

    private fun observeSeasonAnime() {
        val binding = binding
        homeViewModel.seasonAnimeLiveData.observe(viewLifecycleOwner, Observer {
            var i = 0
            seasonAnime.clear()
            while(seasonAnime.size < 6){
                seasonAnime.add(it[i])
                i++
            }
            seasonAdapter.setAniList(seasonAnime)
        })
    }

    private fun prepareSeasonRecyclerView() {
        seasonAdapter = AniAdapter()
        binding.rvPopular.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = seasonAdapter
        }
    }

    private fun observeTrendingAnime() {
        val binding = binding
        homeViewModel.trendingAnimesLiveData.observe(viewLifecycleOwner, Observer {
            var i = 0
            trendingAnime.clear()
            while(trendingAnime.size < 6){
                trendingAnime.add(it[i])
                i++
            }
            trendingAdapter.setAniList(trendingAnime)
        })
    }

    private fun prepareTrendingRecyclerView() {
        trendingAdapter = AniAdapter()
        binding.rvTrending.apply{
            layoutManager = GridLayoutManager(context, 3)
            adapter = trendingAdapter
        }
    }
}