package com.codefororlando.petadoption.recyclerview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import com.codefororlando.petadoption.R

class HorizontalViewPagerIndicator : LinearLayout, ViewPager.OnPageChangeListener {

    private var pageCount = 0
    private var selectedIndex = 0
    private var indicatorViews: MutableList<View> = mutableListOf()
    private lateinit var scaleUpAnim: ScaleAnimation
    private lateinit var scaleDownAnim: ScaleAnimation

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        initAnimations()
    }

    fun bind(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(this)
        pageCount = viewPager.adapter.count
        var indicatorParent: View
        for (i in 0 until pageCount) {
            indicatorParent = createCircleIndicatorView()
            addView(indicatorParent)
            indicatorViews.add(indicatorParent.findViewById(R.id.circle))
        }
        indicatorViews[selectedIndex].startAnimation(scaleUpAnim)
    }

    private fun createCircleIndicatorView(): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.circle_layout, this, false)
    }

    private fun initAnimations() {
        scaleUpAnim = ScaleAnimation(DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                Animation.RELATIVE_TO_SELF,
                .5f,
                Animation.RELATIVE_TO_SELF,
                .5f)
        scaleUpAnim.fillAfter = true
        scaleUpAnim.duration = 300

        scaleDownAnim = ScaleAnimation(LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                LARGE_INDICATOR_SCALE,
                DEFAULT_INDICATOR_SCALE,
                Animation.RELATIVE_TO_SELF,
                .5f,
                Animation.RELATIVE_TO_SELF,
                .5f)
        scaleDownAnim.fillAfter = true
        scaleDownAnim.duration = 300
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (selectedIndex != position) {
            indicatorViews[position].startAnimation(scaleUpAnim)
            indicatorViews[selectedIndex].startAnimation(scaleDownAnim)
        }
        selectedIndex = position
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    companion object {
        private val DEFAULT_INDICATOR_SCALE = 1f
        private val LARGE_INDICATOR_SCALE = 1.3f
    }
}
