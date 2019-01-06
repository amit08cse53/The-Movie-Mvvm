package com.example.themoviedemo.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.example.themoviedemo.api.Api
import com.example.themoviedemo.models.entity.Tv
import kotlinx.android.synthetic.main.item_poster.view.*



class TvListViewHolder(val view: View, private val delegate: Delegate): BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(tv: Tv)
    }

    private lateinit var tv: Tv

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is Tv) {
            tv = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_poster_title.text = tv.name
            tv.poster_path.let {
                Glide.with(context)
                        .load(Api.getPosterPath(it))
                        .listener(GlidePalette.with(Api.getPosterPath(it))
                                .use(BitmapPalette.Profile.VIBRANT)
                                .intoBackground(item_poster_palette)
                                .crossfade(true))
                        .into(item_poster_post)
            }
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(tv)
    }

    override fun onLongClick(p0: View?) = false
}