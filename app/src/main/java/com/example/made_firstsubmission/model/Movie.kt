package com.example.made_firstsubmission.model

import android.os.Parcel
import android.os.Parcelable

class Movie():Parcelable{
    var title:String? = null
    var poster:String? = null
    var description:String? = null
    var rating:String? = null
    var release_date:String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        poster = parcel.readString()
        description = parcel.readString()
        rating = parcel.readString()
        release_date = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster)
        parcel.writeString(description)
        parcel.writeString(rating)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}