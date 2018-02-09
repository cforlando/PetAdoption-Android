package com.codefororlando.petadoption.recyclerview

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import com.codefororlando.petadoption.R

class HorizontalViewPagerIndicator @JvmOverloads constructor(context: Context,
                                                             attrs: AttributeSet? = null,
                                                             defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {

    private var pageCount = 0
    private var selectedIndex = 0
    private val indicatorViews: MutableList<View> = mutableListOf()
    private val scaleUpAnim: ScaleAnimation
    private val scaleDownAnim: ScaleAnimation

    init {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        scaleUpAnim = ScaleAnimation(DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                Animation.RELATIVE_TO_SELF,
                .5f,
                Animation.RELATIVE_TO_SELF,
                .5f)
        scaleDownAnim = ScaleAnimation(LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                Animation.RELATIVE_TO_SELF,
                .5f,
                Animation.RELATIVE_TO_SELF,
                .5f)
        configureAnimations()
    }

    fun bind(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(this)
        viewPager.addOnAdapterChangeListener(this)
    }

    fun onDataSetChanged(adapter: PagerAdapter) {
        clearIndicators()
        pageCount = adapter.count
        var indicatorParent: View
        for (i in 0 until pageCount) {
            indicatorParent = createCircleIndicatorView()
            addView(indicatorParent)
            indicatorViews.add(indicatorParent.findViewById(R.id.circle))
        }

        if(pageCount > 0) {
            indicatorViews[selectedIndex].startAnimation(scaleUpAnim)
        }
    }

    private fun clearIndicators() {
        removeAllViews()
        indicatorViews.clear()
    }

    private fun createCircleIndicatorView(): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.circle_layout, this, false)
    }

    private fun configureAnimations() {
        scaleUpAnim.fillAfter = true
        scaleUpAnim.duration = 300

        scaleDownAnim.fillAfter = true
        scaleDownAnim.duration = 300
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Do nothing
    }

    override fun onPageSelected(position: Int) {
        if (selectedIndex != position) {
            indicatorViews[position].startAnimation(scaleUpAnim)
            indicatorViews[selectedIndex].startAnimation(scaleDownAnim)
        }
        selectedIndex = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        // Do nothing
    }

    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
        if (newAdapter == null) {
            return
        }
        onDataSetChanged(newAdapter)
    }

    companion object {
        private val DEFAULT_INDICATOR_SCALE = 1f
        private val LARGE_INDICATOR_SCALE = 1.3f
    }
}
