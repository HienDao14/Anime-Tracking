package com.example.anilist.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anilist.pojo.AnimeFull
import com.example.anilist.ui.fragment.CharacterFragment
import com.example.anilist.ui.fragment.OverviewFragment
import com.example.anilist.ui.fragment.ReviewFragment
import com.example.anilist.ui.fragment.SocialFragment
import com.example.anilist.ui.fragment.StaffFragment
import com.example.anilist.ui.fragment.StatsFragment
import com.example.anilist.ui.fragment.WatchFragment

class ViewPager2Adapter(fa: FragmentActivity, val id : Int, val anime: AnimeFull) : FragmentStateAdapter(fa){

    private val ARG_OBJECT1 = "malID"
    private val ARG_OBJECT2 = "anime"

    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {val fragment = OverviewFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                    putSerializable(ARG_OBJECT2, anime)
                }
                return fragment

            }
            1 -> {
                val fragment = WatchFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                }
                return fragment
            }
            2 -> {
                val fragment = CharacterFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                }
                return fragment
            }
            3 -> {
                val fragment = StaffFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                }
                return fragment
            }
            4 -> {
                val fragment = ReviewFragment()
                fragment.arguments = Bundle().apply {
                    putSerializable(ARG_OBJECT1, anime)
                }
                return fragment
            }
            5 -> {
                val fragment = StatsFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                }
                return fragment
            }
            else -> {
                val fragment = SocialFragment()
                fragment.arguments = Bundle().apply {
                    putInt(ARG_OBJECT1, id)
                }
                return fragment
            }
        }
    }
}