package com.example.anilist.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anilist.DetailFragment
import com.example.anilist.R
import com.example.anilist.adapter.CharacterAdapter
import com.example.anilist.databinding.FragmentCharacterBinding
import com.example.anilist.viewModel.CharacterViewModel
import com.example.anilist.viewModel.DetailViewModel

private const val ARG_PARAM1 = "malID"

class CharacterFragment() : Fragment() {
    private lateinit var binding : FragmentCharacterBinding
    private lateinit var characterAdapter : CharacterAdapter
    private var isEnglish : Int = 0
    private var animeId: Int = 0
    private val viewModel : CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterBinding.inflate(inflater, container, false)

        val types = resources.getStringArray(R.array.language)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_selected, types)
        arrayAdapter.setDropDownViewResource(R.layout.drop_down_item)
        binding.spinner.adapter = arrayAdapter

        characterAdapter = CharacterAdapter()
        characterAdapter.setIsEnglish(isEnglish)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCharacterRecyclerView()
        binding.characterLoading.visibility = View.VISIBLE
        viewModel.setLoadingBar(binding.characterLoading)
        viewModel.getCharacterLiveData(animeId)
        observeCharacterLiveData()
        onSpinnerItemSelected()
    }

    private fun onSpinnerItemSelected() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                isEnglish = p2
                characterAdapter.setIsEnglish(isEnglish)
                observeCharacterLiveData()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun observeCharacterLiveData() {
        viewModel.characterLiveData.observe(viewLifecycleOwner, Observer {
            characterAdapter.setListCharacter(it.data)
        })
    }

    private fun prepareCharacterRecyclerView() {
        binding.rvCharacter.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = characterAdapter
        }
    }
}