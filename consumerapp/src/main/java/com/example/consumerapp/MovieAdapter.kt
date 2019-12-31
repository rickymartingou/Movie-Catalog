package com.example.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieAdapter(var items: List<Movie>):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MovieViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie_list, viewGroup, false)
        return MovieViewHolder(mView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.bind(items[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView){
                item_movie_title.text = movie.title
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w185"+movie.poster).into(item_movie_image)
            }
        }
    }
}