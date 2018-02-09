package com.codefororlando.petadoption.view

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.codefororlando.petadoption.R
import com.squareup.picasso.Picasso

internal class PetImageViewPagerAdapter(context: Context) : PagerAdapter() {

    private var images: MutableList<String> = mutableListOf()
    @DrawableRes
    private var placeholderImage: Int = 0
    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setImages(imageList: List<String>, @DrawableRes placeholderImage: Int) {
        images.clear()
        images.addAll(imageList)
        this.placeholderImage = placeholderImage
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.image_pager_item, container, false)
        val imageView = itemView.findViewById(R.id.image_content) as ImageView
        Picasso.with(container.context)
                .load(images[position])
                .resize(1000, 1000)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(placeholderImage)
                .into(imageView)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
