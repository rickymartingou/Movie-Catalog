package com.example.made_firstsubmission.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.made_firstsubmission.model.Movie

class MyHelper(context: Context){
    private val MOVIE = "FavoriteMovie"
    private val TV = "FavoriteTV"

    companion object {
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: MyHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): MyHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MyHelper(context)
                    }
                }
            }
            return INSTANCE as MyHelper
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAllMovie(): Cursor {
        return database.query(
            MOVIE,
            null,
            null,
            null,
            null,
            null,
            "Id ASC"
        )
    }

    fun queryMovieByTitle(title:String): Cursor {
        return database.query(
            MOVIE,
            null,
            null,
            null,
            "Title",
            "Title = '$title'",
            null
        )
    }

    fun queryAllTV(): Cursor {
        return database.query(
            TV,
            null,
            null,
            null,
            null,
            null,
            "Id ASC"
        )
    }

    fun queryTVByTitle(title:String): Cursor {
        return database.query(
            TV,
            null,
            null,
            null,
            "Title",
            "Title = '$title'",
            null
        )
    }

    fun insertMovie(movie: Movie): Long {
        val args = ContentValues()
        args.put("Title", movie.title)
        args.put("Poster", movie.poster)
        args.put("Description", movie.description)
        args.put("Rating", movie.rating)
        args.put("ReleaseDate", movie.release_date)
        return database.insert(MOVIE, null, args)
    }

    fun deleteMovie(id: String): Int {
        return database.delete(MOVIE, "Title = '$id'", null)
    }

    fun insertTV(movie: Movie): Long {
        val args = ContentValues()
        args.put("Title", movie.title)
        args.put("Poster", movie.poster)
        args.put("Description", movie.description)
        args.put("Rating", movie.rating)
        args.put("ReleaseDate", movie.release_date)
        return database.insert(TV, null, args)
    }

    fun deleteTV(id: String): Int {
        return database.delete(TV, "Title = '$id'", null)
    }
}