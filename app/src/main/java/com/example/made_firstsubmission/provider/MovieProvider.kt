package com.example.made_firstsubmission.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.made_firstsubmission.db.MyHelper

//com.ricky.submisi
class MovieProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private const val TV = 3
        private const val TV_ID = 4
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val AUTHORITY = "com.ricky.submisi"
        private const val MOVIE_TABLE = "FavoriteMovie"
        private const val TV_TABLE = "FavoriteTV"
        private lateinit var myHelper : MyHelper

        init {
            sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE, MOVIE)
            sUriMatcher.addURI(AUTHORITY,
                "$MOVIE_TABLE/#",
                MOVIE_ID)

            sUriMatcher.addURI(AUTHORITY, TV_TABLE, TV)
            sUriMatcher.addURI(AUTHORITY,
                "$TV_TABLE/#",
                TV_ID)
        }
    }

    override fun onCreate(): Boolean {
        myHelper = MyHelper.getInstance(context as Context)
        myHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor : Cursor?
        when(sUriMatcher.match(uri)){
            MOVIE -> cursor = myHelper.queryAllMovie()
            MOVIE_ID -> cursor = myHelper.queryMovieByTitle(uri.lastPathSegment.toString())
            TV -> cursor = myHelper.queryAllTV()
            TV_ID -> cursor = myHelper.queryTVByTitle(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }
}
