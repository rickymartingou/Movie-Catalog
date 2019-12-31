package com.example.consumerapp

import android.os.Parcel
import android.os.Parcelable

class Movie():Parcelable{
    var title:String? = null
    var poster:String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        poster = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster)
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