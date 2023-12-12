package com.example.anilist.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anilist.adapter.RelationAdapter
import com.example.anilist.databinding.FragmentOverviewBinding
import com.example.anilist.pojo.AnimeFull
import com.example.anilist.pojo.EntryX
import com.example.anilist.pojo.RelationWithImage
import com.example.anilist.viewModel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "malID"
private const val ARG_PARAM2 = "anime"

class OverviewFragment() : Fragment() {
    private var animeId: Int = 0
    private lateinit var anime : AnimeFull
    private val relations = ArrayList<RelationWithImage>()
    private lateinit var binding : FragmentOverviewBinding
    private lateinit var relationAdapter : RelationAdapter
    private val viewModel: DetailViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeId = it.getInt(ARG_PARAM1)
            anime = it.getSerializable(ARG_PARAM2, AnimeFull::class.java)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDetail.text = anime.data.synopsis
        prepareRelationRecyclerView()
        var relationType = ""
        var malId = 0
        var name = ""
        var type = ""
        var url = ""
        Log.d("OverviewFragment", animeId.toString() + "   " + anime.data.title)
        val relation = anime.data.relations
        relation.forEach {relationIt ->
            relationType = relationIt.relation
            relationIt.entry.forEach {
                malId = it.mal_id
                name = it.name
                type = it.type
                url = it.url
                if(it.type == "manga"){
                    observeMangaLiveData(malId, name, type, url, relationType)
                } else {
                    observeAnimeLiveData(malId, name, type, url, relationType)
                }
            }
        }
    }

    private fun observeAnimeLiveData(malId: Int, name: String, type: String, url: String, relationType: String){
        Log.d("OverviewFragment", malId.toString())
        viewModel.getAnimeById(malId)
        viewModel.animeFullLiveData.observe(viewLifecycleOwner, Observer{
            val imageUrl = it.data.images.webp.image_url
            Log.d("OverviewFragment", it.data.images.webp.image_url)
            relations.add(RelationWithImage(relationType, EntryX(malId, name, type, url), imageUrl))
            relationAdapter.setUpRelation(relations)
        })
    }

    private fun prepareRelationRecyclerView() {
        relationAdapter = RelationAdapter()
        binding.rvRelation.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = relationAdapter
        }
    }

    private fun observeMangaLiveData(malId : Int, name: String, type: String, url: String, relationType: String) {
        viewModel.getMangaById(malId)
        viewModel.mangaFullLiveData.observe(viewLifecycleOwner, Observer {
            val imageUrl = it.data.images.jpg.image_url
            relations.add(RelationWithImage(relationType, EntryX(malId, name, type, url), imageUrl))
            relationAdapter.setUpRelation(relations)
        })
    }

}