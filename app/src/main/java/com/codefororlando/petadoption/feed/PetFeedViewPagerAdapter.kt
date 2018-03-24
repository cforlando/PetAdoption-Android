package com.codefororlando.petadoption.feed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.codefororlando.petadoption.feed.base.AbstractPetFeedFragment
import com.codefororlando.petadoption.feed.popular.PopularPetFeedFragment
import com.codefororlando.petadoption.feed.watching.WatchingPetFeedFragment

class PetFeedViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val POPULAR_FEED_INDEX = 0
    private val FOLLOWING_FEED_INDEX = 1
    private val SIZE = 2

    private val fragmentList = mutableListOf<AbstractPetFeedFragment<*, *>>()

    override fun getCount(): Int {
        return SIZE
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            POPULAR_FEED_INDEX -> {
                fragmentList.add(POPULAR_FEED_INDEX, PopularPetFeedFragment())
                fragmentList[POPULAR_FEED_INDEX]
            }
            FOLLOWING_FEED_INDEX -> {
                fragmentList.add(FOLLOWING_FEED_INDEX, WatchingPetFeedFragment())
                fragmentList[FOLLOWING_FEED_INDEX]
            }
            else -> throw IllegalArgumentException("Invalid Pager Item Index")
        }
    }

    fun getFragment(position: Int): AbstractPetFeedFragment<*, *> {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            POPULAR_FEED_INDEX -> "Popular"
            FOLLOWING_FEED_INDEX -> "Following"
            else -> throw IllegalArgumentException("Invalid Pager Item Index")
        }
    }
}