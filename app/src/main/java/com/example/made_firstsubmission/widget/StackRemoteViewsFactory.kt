package com.example.made_firstsubmission.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.made_firstsubmission.R
import com.example.made_firstsubmission.db.MyHelper

internal class StackRemoteViewsFactory (private val mContext: Context): RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = ArrayList<String>()
    private lateinit var db : MyHelper

    override fun onCreate() {
        getData()
        println(mWidgetItems)
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.favorite_widget_item)
        val futureTarget = Glide.with(mContext)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w185/${mWidgetItems.get(p0)}")
            .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
        try {
            rv.setImageViewBitmap(R.id.imageViewWidget, futureTarget.get())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rv
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }

    override fun onDataSetChanged() {

    }

    private fun getData(){
        db = MyHelper.getInstance(mContext)
        db.open()

        var temp: String
        val result = db.queryAllMovie()

        while (result.moveToNext()) {
            temp = result.getString(result.getColumnIndex("Poster"))
            mWidgetItems.add(temp)
        }
//        db.close()
    }
}