package com.example.made_firstsubmission.activity


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.adapter.MovieAdapter
import com.example.made_firstsubmission.network.Connectivity
import com.example.made_firstsubmission.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    private lateinit var adapter : MovieAdapter
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var progressBar : ProgressBar
    private lateinit var searchView : SearchView
    private lateinit var viewOfLayout : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_movie, container, false)
        progressBar = viewOfLayout.progressBar
        searchView = viewOfLayout.searchMovie

        adapter = MovieAdapter {
            val intent = Intent(activity, DetailMovie::class.java)
            intent.putExtra("data",it)
            intent.putExtra("type","Movie")
            startActivity(intent)
        }
        adapter.notifyDataSetChanged()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    if(newText.isEmpty()){
                        getData(null)
                    }
                    else{
                        getData(newText)
                    }
                }
                return false
            }

        })

        val list = viewOfLayout.movie_list
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter

        movieViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MovieViewModel::class.java)

        getData(null)

        movieViewModel.getMovie().observe(this, Observer { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                showLoading(false)
            }
        })

        return viewOfLayout
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun getData(query:String?){
        //Check if there is an internet connection or not
        val connect = Connectivity()
        val isConnected = connect.verifyAvailableNetwork(viewOfLayout.context)
        if(isConnected){
            viewOfLayout.movie_list_status.visibility = View.GONE
            if(query != null){
                movieViewModel.setMovieByQuery(query)
            }else{
                movieViewModel.setMovie()
            }
            showLoading(true)
        }
        else{
            viewOfLayout.movie_list_status.visibility = View.VISIBLE
        }
    }
}
