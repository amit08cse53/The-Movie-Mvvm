package com.example.themoviedemo.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.example.themoviedemo.R
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.Review
import com.example.themoviedemo.view.viewholder.ReviewListViewHolder



class ReviewListAdapter: BaseAdapter() {

    init {
        addSection(ArrayList<Review>())
    }

    fun addReviewList(resource: Resource<List<Review>>) {
        resource.data?.let {
            sections[0].addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_review
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return ReviewListViewHolder(view)
    }
}
