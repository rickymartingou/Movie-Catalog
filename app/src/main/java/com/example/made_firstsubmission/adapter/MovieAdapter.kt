package com.example.made_firstsubmission.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.model.Movie
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieAdapter(private val listener:(Movie) -> Unit):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    private val mData = ArrayList<Movie>()

    fun setData(items: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MovieViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie_list, viewGroup, false)
        return MovieViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.bind(mData[position],listener)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, listener: (Movie) -> Unit) {
            with(itemView){
                item_movie_title.text = movie.title
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w185"+movie.poster).into(item_movie_image)
                itemView.setOnClickListener { listener(movie) }
            }
        }
    }
}