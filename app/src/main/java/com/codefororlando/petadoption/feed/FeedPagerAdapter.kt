package com.codefororlando.petadoption.feed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FeedPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val POPULAR_FEED_INDEX = 0
    private val FOLLOWING_FEED_INDEX = 1
    private val SIZE = 2

    override fun getCount(): Int {
        return SIZE
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            POPULAR_FEED_INDEX -> PetFeedFragment()
            FOLLOWING_FEED_INDEX -> PetFeedFragment()
            else -> throw IllegalArgumentException("Invalid Pager Item Index")
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