package com.example.themoviedemo.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.example.themoviedemo.R
import com.example.themoviedemo.models.entity.Movie
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.view.viewholder.MovieListViewHolder



class MovieListAdapter(private val delegate: MovieListViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Movie>())
    }

    fun addMovieList(resource: Resource<List<Movie>>) {
        resource.data?.let {
            sections[0].addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_poster
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MovieListViewHolder(view, delegate)
    }
}
