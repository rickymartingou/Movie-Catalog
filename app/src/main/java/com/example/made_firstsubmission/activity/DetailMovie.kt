package com.example.made_firstsubmission.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.db.MyHelper
import com.example.made_firstsubmission.model.Movie
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovie : AppCompatActivity() {

    private lateinit var db : MyHelper
    private lateinit var movie : Movie
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var resultTitle: String? =  null
    private lateinit var type: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu,menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        db = MyHelper.getInstance(this)
        db.open()

        val data = intent.getParcelableExtra<Movie>("data")
        type = intent.getStringExtra("type")

        detail_title.text = data.title
        detail_desc.text = data.description
        detail_rating.text = data.rating
        detail_release.text = data.release_date
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"+data.poster).into(detail_image)

        movie = Movie()
        movie.title = data.title
        movie.poster = data.poster
        movie.description = data.description
        movie.rating = data.rating
        movie.release_date = data.release_date

        favoriteState(movie.title!!)
    }

    private fun addToFavorite(){

        if(type == "Movie"){
            try {
                db.insertMovie(movie)
                Toast.makeText(this,"Added To Favorite",Toast.LENGTH_SHORT).show()
            } catch (e: SQLiteConstraintException){
                Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
        else if(type == "TV"){
            try {
                db.insertTV(movie)
                Toast.makeText(this,"Added To Favorite",Toast.LENGTH_SHORT).show()
            } catch (e: SQLiteConstraintException){
                Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun removeFromFavorite(){
        if(type == "Movie"){
            try {
                db.deleteMovie(movie.title!!)
                Toast.makeText(this,"Removed From Favorite",Toast.LENGTH_SHORT).show()
            } catch (e: SQLiteConstraintException){
                Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
        else if(type == "TV"){
            try {
                db.deleteTV(movie.title!!)
                Toast.makeText(this,"Removed From Favorite",Toast.LENGTH_SHORT).show()
            } catch (e: SQLiteConstraintException){
                Toast.makeText(this,e.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite_border_black_24dp)
    }

    private fun favoriteState(title:String){
        if(type == "Movie"){
            val result = db.queryMovieByTitle(title)
            val isAdded:Boolean = result.moveToNext()
            if(isAdded){
                resultTitle = result.getString(result.getColumnIndex("Title"))
            }

            if (resultTitle!= null) isFavorite = true
        }
        else if(type == "TV"){
            val result = db.queryTVByTitle(title)
            val isAdded:Boolean = result.moveToNext()
            if(isAdded){
                resultTitle = result.getString(result.getColumnIndex("Title"))
            }

            if (resultTitle!= null) isFavorite = true
        }

    }
}
