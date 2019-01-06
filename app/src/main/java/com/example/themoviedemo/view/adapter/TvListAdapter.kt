package com.example.themoviedemo.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.example.themoviedemo.R
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.entity.Tv
import com.example.themoviedemo.view.viewholder.TvListViewHolder



class TvListAdapter(private val delegate: TvListViewHolder.Delegate): BaseAdapter() {

    init {
        addSection(ArrayList<Tv>())
    }

    fun addTvList(resource: Resource<List<Tv>>) {
        resource.data?.let {
            sections[0].addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_poster
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return TvListViewHolder(view, delegate)
    }
}
