package com.example.themoviedemo.models.entity

import androidx.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.example.themoviedemo.models.Keyword
import com.example.themoviedemo.models.Review
import com.example.themoviedemo.models.Video
//import com.example.themoviedemo.models.Keyword
//import com.example.themoviedemo.models.Review
//import com.example.themoviedemo.models.Video



@Entity(primaryKeys = [("id")])
data class Tv(var page: Int,
              var keywords: List<Keyword>? = ArrayList(),
              var videos: List<Video>? = ArrayList(),
              var reviews: List<Review>? = ArrayList(),
              val poster_path: String,
              val popularity: Float,
              val id: Int,
              val backdrop_path: String?,
              val vote_average: Float,
              val overview: String,
              val first_air_date: String,
              val origin_country: List<String>,
              val genre_ids: List<Int>,
              val original_language: String,
              val vote_count: Int,
              val name: String,
              val original_name: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            ArrayList<Keyword>().apply { source.readList(this, Keyword::class.java.classLoader) },
            ArrayList<Video>().apply { source.readList(this, Video::class.java.classLoader) },
            ArrayList<Review>().apply { source.readList(this, Review::class.java.classLoader) },
            source.readString(),
            source.readFloat(),
            source.readInt(),
            source.readString(),
            source.readFloat(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeList(keywords)
        writeList(videos)
        writeList(reviews)
        writeString(poster_path)
        writeFloat(popularity)
        writeInt(id)
        writeString(backdrop_path)
        writeFloat(vote_average)
        writeString(overview)
        writeString(first_air_date)
        writeStringList(origin_country)
        writeList(genre_ids)
        writeString(original_language)
        writeInt(vote_count)
        writeString(name)
        writeString(original_name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Tv> = object : Parcelable.Creator<Tv> {
            override fun createFromParcel(source: Parcel): Tv = Tv(source)
            override fun newArray(size: Int): Array<Tv?> = arrayOfNulls(size)
        }
    }
}