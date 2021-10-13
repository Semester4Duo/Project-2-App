package nl.dylandavid.project2.duoapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter : FragmentStateAdapter {
    private var numOfTabs: Int

    constructor(fm : FragmentManager, lf : Lifecycle, numOfTabs : Int) : super(fm, lf) {
        this.numOfTabs = numOfTabs
    }

    override fun getItemCount(): Int {
        return numOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return Tab1Fragment()
            1 -> return Tab2Fragment()
            2 -> return Tab3Fragment()
        }
        return Fragment()
    }

}