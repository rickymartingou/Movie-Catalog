package com.example.made_firstsubmission.activity


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.adapter.TVAdapter
import com.example.made_firstsubmission.network.Connectivity
import com.example.made_firstsubmission.viewmodel.TVViewModel
import kotlinx.android.synthetic.main.fragment_tv.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class TVFragment : Fragment() {

    private lateinit var adapter : TVAdapter
    private lateinit var tvViewModel : TVViewModel
    private lateinit var progressBar : ProgressBar
    private lateinit var viewOfLayout : View
    private lateinit var searchView : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_tv, container, false)
        progressBar = viewOfLayout.progressBarTV
        searchView = viewOfLayout.searchTv

        adapter = TVAdapter {
            val intent = Intent(activity, DetailMovie::class.java)
            intent.putExtra("data",it)
            intent.putExtra("type","TV")
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

        val list = viewOfLayout.movie_list_tv
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter

        tvViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(TVViewModel::class.java)

        getData(null)

        tvViewModel.getTV().observe(this, Observer { movieItems ->
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

    private fun getData(query: String?){
        val connect = Connectivity()
        val isConnected = connect.verifyAvailableNetwork(viewOfLayout.context)
        if(isConnected) {
            viewOfLayout.tv_list_status.visibility = View.GONE
            if(query != null){
                tvViewModel.setTvByQuery(query)
            }else{
                tvViewModel.setTV()
            }
            showLoading(true)
        }
        else{
            viewOfLayout.tv_list_status.visibility = View.VISIBLE
        }
    }
}
