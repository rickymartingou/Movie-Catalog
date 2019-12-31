package com.example.consumerapp

import android.database.Cursor

object Mapper {

    fun mapCurIntoList(cursor: Cursor): List<Movie> {
        val list = ArrayList<Movie>()

        while (cursor.moveToNext()) {
            val movie = Movie()
            movie.title = cursor.getString(cursor.getColumnIndex("Title"))
            movie.poster = cursor.getString(cursor.getColumnIndex("Poster"))
            list.add(movie)
        }
        return list
    }
}