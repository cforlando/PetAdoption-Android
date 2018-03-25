package com.codefororlando.petadoption.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.codefororlando.petadoption.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.open_source_list_item.view.*
import android.view.LayoutInflater
import android.view.MenuItem
import com.bluelinelabs.logansquare.LoganSquare
import io.reactivex.Observable
import timber.log.Timber


class OpenSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bind() {
        val recylcerView = findViewById(R.id.recyclerView) as RecyclerView;
        recylcerView.layoutManager = LinearLayoutManager(this)
        loadOpenSourceItems().subscribe({ items ->
            val adapter = OpenSourceActivity.OpenSourceListAdapter(items, this::onItemClick)
            recylcerView.adapter = adapter
        }, Timber::d)
    }

    fun onItemClick(item: OpenSourceModel) {
        val intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse(item.link)
        startActivity(intent)
    }

    fun loadOpenSourceItems(): Observable<List<OpenSourceModel>> {
        return Observable.fromCallable {
            val inputStream = resources.openRawResource(R.raw.dep)
            LoganSquare.parseList(inputStream, OpenSourceModel::class.java)
        }
    }

    class OpenSourceListAdapter(val items: List<OpenSourceModel>, val listener: (OpenSourceModel) -> Unit) : RecyclerView.Adapter<OpenSourceListAdapter.OpenSourceItemViewHolder>() {

        override fun onBindViewHolder(holder: OpenSourceItemViewHolder?, position: Int) {
            holder?.bind(items[position], listener)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OpenSourceItemViewHolder? {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.open_source_list_item, parent, false);
            return OpenSourceItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        class OpenSourceItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

            fun bind(model: OpenSourceModel, listener: (OpenSourceModel) -> Unit) = with(itemView) {
                containerView.title.text = model.name
                containerView.description.text = model.description
                setOnClickListener({ listener(model) })
            }
        }
    }
}