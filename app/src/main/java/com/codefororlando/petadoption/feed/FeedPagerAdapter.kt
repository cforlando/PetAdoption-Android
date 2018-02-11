package com.codefororlando.petadoption.feed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.codefororlando.petadoption.feed.popular.PopularPetFeedFragment
import com.codefororlando.petadoption.feed.watching.WatchingPetFeedFragment

class FeedPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val POPULAR_FEED_INDEX = 0
    private val FOLLOWING_FEED_INDEX = 1
    private val SIZE = 2

    override fun getCount(): Int {
        return SIZE
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            POPULAR_FEED_INDEX -> PopularPetFeedFragment()
            FOLLOWING_FEED_INDEX -> WatchingPetFeedFragment()
            else -> throw IllegalArgumentException("Invalid Pager Item Index") as Throwable
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            POPULAR_FEED_INDEX -> "Popular"
            FOLLOWING_FEED_INDEX -> "Following"
            else -> throw IllegalArgumentException("Invalid Pager Item Index")
        }
    }
}