package com.example.consumerapp

import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_tv.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteTVFragment : Fragment() {

    private lateinit var adapter : MovieAdapter
    private var movieList= ArrayList<Movie>()
    private lateinit var viewOfLayout : View
    private val URI = Uri.parse("content://com.ricky.submisi/FavoriteTV")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout =  inflater.inflate(R.layout.fragment_favorite_tv, container, false)

        adapter = MovieAdapter(listOf())
        val list = viewOfLayout.favorite_tv_list
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                getData()
            }
        }

        requireContext().contentResolver.registerContentObserver(URI, true, myObserver)
        getData()
        return viewOfLayout
    }

    private fun getData(){
        movieList.clear()
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = requireContext().contentResolver
                    .query(URI, null, null, null, null) as Cursor
                Mapper.mapCurIntoList(cursor)
            }
            val movies = deferredNotes.await()
            if (movies.isNotEmpty()){
                adapter.items = movies
                viewOfLayout.tv_no_data.visibility = View.GONE
            }
            else{
                adapter.items = listOf()
                viewOfLayout.tv_no_data.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }

    }
}