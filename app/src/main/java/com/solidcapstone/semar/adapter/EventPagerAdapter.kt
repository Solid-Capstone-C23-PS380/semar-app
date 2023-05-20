package com.solidcapstone.semar.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.solidcapstone.semar.ui.event.ActiveEventFragment
import com.solidcapstone.semar.ui.event.MissedEventFragment
import java.util.Date

class EventPagerAdapter(activity: AppCompatActivity, var date: Date) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MissedEventFragment(date)
            1 -> fragment = ActiveEventFragment(date)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}