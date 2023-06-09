package com.solidcapstone.semar.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solidcapstone.semar.ui.event.ActiveEventFragment
import com.solidcapstone.semar.ui.event.MissedEventFragment

class EventPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MissedEventFragment()
            1 -> fragment = ActiveEventFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}