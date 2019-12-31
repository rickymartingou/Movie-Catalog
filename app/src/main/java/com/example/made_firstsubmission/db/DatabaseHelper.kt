package com.example.made_firstsubmission.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE FavoriteMovie" +
                " (Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Title TEXT NOT NULL," +
                " Poster TEXT NOT NULL," +
                " Description TEXT NOT NULL," +
                " Rating TEXT NOT NULL," +
                " ReleaseDate TEXT NOT NULL)"

        private val SQL_CREATE_TABLE_TV = "CREATE TABLE FavoriteTV" +
                " (Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Title TEXT NOT NULL," +
                " Poster TEXT NOT NULL," +
                " Description TEXT NOT NULL," +
                " Rating TEXT NOT NULL," +
                " ReleaseDate TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

    }


}