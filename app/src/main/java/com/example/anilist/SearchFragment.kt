package com.example.anilist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.anilist.adapter.AniAdapter
import com.example.anilist.databinding.FragmentSearchBinding
import com.example.anilist.viewModel.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var animeAdapter : AniAdapter
    private lateinit var searchText : String
    private val viewModel : SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchText = arguments?.getString("searchText")!!
        binding.searchField.setText(searchText)

        val types = resources.getStringArray(R.array.type)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_selected, types)
        arrayAdapter.setDropDownViewResource(R.layout.drop_down_item)
        binding.spinner.spinner.adapter = arrayAdapter

        viewModel.setSearchText(searchText)
        animeAdapter = AniAdapter()
        animeAdapter.setLayout("Search")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnCLickSearch()
        prepareAnimeRecyclerView()
        viewModel.getSearchAnime()
        observeSearchAnimeLiveData()
    }

    private fun setOnCLickSearch() {
        binding.searchField.setOnEditorActionListener { v, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.setSearchText(binding.searchField.text.toString())
                viewModel.getSearchAnime()
                observeSearchAnimeLiveData()
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun observeSearchAnimeLiveData() {
        viewModel.searchAnimeLiveData.observe(viewLifecycleOwner, Observer {
            animeAdapter.setAniList(it)
        })
    }

    private fun prepareAnimeRecyclerView() {
        binding.rvAnimeSearch.adapter = animeAdapter
    }
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}