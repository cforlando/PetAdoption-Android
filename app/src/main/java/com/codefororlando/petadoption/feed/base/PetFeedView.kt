package com.codefororlando.petadoption.feed.base

import android.support.v7.widget.RecyclerView
import com.codefororlando.petadoption.data.model.Animal
import io.reactivex.Observable

interface PetFeedView {
    fun refreshList()
    fun showEmptyView()
    fun hideEmptyView()
    fun showContentView()
    fun hideContentView()
    fun setAdapter(adapter: RecyclerView.Adapter<*>);
    fun navigateToDetailView(animal: Animal)
    fun scrollToPosition(index: Int)
    fun getLastVisibleItemIndex() : Int
    fun scrollToTop()
    fun getOnScrollEndObservable() : Observable<Any>
}