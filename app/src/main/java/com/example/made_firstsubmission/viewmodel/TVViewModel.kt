package com.example.made_firstsubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.made_firstsubmission.BuildConfig
import com.example.made_firstsubmission.model.Movie
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TVViewModel : ViewModel (){
    companion object{
        private const val API_KEY = "55a9d3a7e80ca106423efab27c835ce5"
    }

    val listTv = MutableLiveData<ArrayList<Movie>>()

    internal fun setTV() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val tv = list.getJSONObject(i)
                        val tvs = Movie()
                        tvs.title = tv.getString("name")
                        tvs.description = tv.getString("overview")
                        tvs.poster= tv.getString("poster_path")
                        tvs.rating= tv.getString("vote_average")
                        tvs.release_date= tv.getString("first_air_date")
                        listItems.add(tvs)
                    }
                    listTv.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getTV(): LiveData<ArrayList<Movie>> {
        return listTv
    }

    internal fun setTvByQuery(title:String){
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/search/tv?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US&query=$title"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val tv = list.getJSONObject(i)
                        val tvs = Movie()
                        tvs.title = tv.getString("name")
                        tvs.description = tv.getString("overview")
                        tvs.poster= tv.getString("poster_path")
                        tvs.rating= tv.getString("vote_average")
                        tvs.release_date= tv.getString("first_air_date")
                        listItems.add(tvs)
                    }
                    listTv.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
}