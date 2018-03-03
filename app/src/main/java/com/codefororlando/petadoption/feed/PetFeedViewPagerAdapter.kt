package com.codefororlando.petadoption.feed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.codefororlando.petadoption.feed.base.PetFeedView
import com.codefororlando.petadoption.feed.popular.PopularPetFeedFragment
import com.codefororlando.petadoption.feed.watching.WatchingPetFeedFragment

class PetFeedViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val POPULAR_FEED_INDEX = 0
    private val FOLLOWING_FEED_INDEX = 1
    private val SIZE = 2

    var mCurrentItem: PetFeedView? = null

    override fun getCount(): Int {
        return SIZE
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.setPrimaryItem(container, position, `object`)
        if (mCurrentItem !== `object`) {
            mCurrentItem = `object` as PetFeedView
        }
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            POPULAR_FEED_INDEX -> PopularPetFeedFragment()
            FOLLOWING_FEED_INDEX -> WatchingPetFeedFragment()
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