package com.example.anilist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.anilist.adapter.ViewPager2Adapter
import com.example.anilist.databinding.FragmentDetailBinding
import com.example.anilist.pojo.Anime
import com.example.anilist.pojo.AnimeFull
import com.example.anilist.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class DetailFragment : Fragment() {
    private lateinit var binding : FragmentDetailBinding
    private lateinit var anime : AnimeFull
    private var imageUrl: String = ""
    private var youtubeId: String = ""
    private var animeTitle: String = ""
    private var malId : Int = 0
    private val args : DetailFragmentArgs by navArgs()
    private val viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        malId = args.malId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DetailViewModel", "start")
        viewModel.getAnimeById(malId)
        observeAnimeFullLiveData()
        Log.d("DetailFragment", malId.toString())
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.animeFullLiveData.removeObservers(this)
//
//    }

    private fun bindAnotherView() {
        Glide.with(requireActivity())
            .load(imageUrl)
            .into(binding.ivImage)

        binding.tvTitle.text = animeTitle

        binding.tvFormat.text = anime.data.type
        binding.tvEpisodes.text = anime.data.episodes.toString()
        binding.tvEpisodeDuration.text = anime.data.duration
        binding.tvStartDate.text = anime.data.aired.string
        if(anime.data.airing){
            binding.layoutEndDate.visibility = View.GONE
        } else {
            binding.tvEndDate.text = anime.data.aired.to.toString()
        }
        binding.tvSeason.text = anime.data.season + " " + anime.data.year
        binding.tvStudios.text = anime.data.studios[0].name
        binding.tvSource.text = anime.data.source
        var genres = ""
        anime.data.genres.forEachIndexed { index, genre ->
            if(index != anime.data.genres.size - 1){
                genres = genres + genre.name + ", "
            } else genres += genre.name
        }
        binding.tvGenres.text = genres
        binding.tvTitleRomaji.text = anime.data.title
        binding.tvTitleEnglish.text = anime.data.title_english
    }

    private fun bindVideoPlayer() {

        val playerView = binding.ivPoster
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = youtubeId
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        lifecycle.addObserver(playerView)
    }

    private fun observeAnimeFullLiveData() {
        viewModel.animeFullLiveData.observe(viewLifecycleOwner, Observer {
            this.anime = it
            imageUrl = anime.data.images.jpg.image_url
            youtubeId = anime.data.trailer.youtube_id!!
            animeTitle = anime.data.title
            bindVideoPlayer()
            bindAnotherView()
            binding.viewPager2.adapter = ViewPager2Adapter(requireActivity(), malId, anime)
            TabLayoutMediator(binding.tabLayout, binding.viewPager2) {tab, position ->
                when(position){
                    0 -> tab.text = "Overview"
                    1 -> tab.text = "Watch"
                    2 -> tab.text = "Characters"
                    3 -> tab.text = "Staff"
                    4 -> tab.text = "Review"
                    5 -> tab.text = "Stats"
                    6 -> tab.text = "Social"
                }
            }.attach()
        })
    }
}