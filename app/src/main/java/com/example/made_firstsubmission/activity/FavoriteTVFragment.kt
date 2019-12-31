package com.example.made_firstsubmission.activity


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.adapter.MovieAdapter
import com.example.made_firstsubmission.db.MyHelper
import com.example.made_firstsubmission.model.Movie
import kotlinx.android.synthetic.main.fragment_favorite_tv.view.*

class FavoriteTVFragment : Fragment() {

    private lateinit var db : MyHelper
    private lateinit var adapter : MovieAdapter
    private var movieList= ArrayList<Movie>()
    private lateinit var viewOfLayout : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout =  inflater.inflate(R.layout.fragment_favorite_tv, container, false)

        getData()

        if(movieList.size == 0){
            viewOfLayout.favorite_tv_list_status.visibility = View.VISIBLE
        }
        else {
            viewOfLayout.favorite_tv_list_status.visibility = View.GONE
            adapter = MovieAdapter {
                val intent = Intent(activity, DetailMovie::class.java)
                intent.putExtra("data", it)
                intent.putExtra("type", "TV")
                startActivity(intent)
            }
            adapter.setData(movieList)
            adapter.notifyDataSetChanged()

            val list = viewOfLayout.favorite_tv_list
            list.layoutManager = LinearLayoutManager(requireContext())
            list.adapter = adapter
        }

        return viewOfLayout
    }

    override fun onResume() {
        super.onResume()
        getData()

        if(::adapter.isInitialized){
            adapter.setData(movieList)
            adapter.notifyDataSetChanged()
        }

        if(movieList.size == 0){
            viewOfLayout.favorite_tv_list_status.visibility = View.VISIBLE
        }
        else{
            viewOfLayout.favorite_tv_list_status.visibility = View.GONE
        }
    }

    private fun getData(){
        movieList.clear()
        db = MyHelper.getInstance(requireContext())
        db.open()

        val result = db.queryAllTV()

        while (result.moveToNext()) {
            val movie = Movie()
            movie.title = result.getString(result.getColumnIndex("Title"))
            movie.poster = result.getString(result.getColumnIndex("Poster"))
            movie.description = result.getString(result.getColumnIndex("Description"))
            movie.rating = result.getString(result.getColumnIndex("Rating"))
            movie.release_date = result.getString(result.getColumnIndex("ReleaseDate"))

            movieList.add(movie)
        }
//        db.close()
    }
}
