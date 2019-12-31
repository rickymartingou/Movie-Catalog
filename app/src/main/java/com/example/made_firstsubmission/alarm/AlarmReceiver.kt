package com.example.made_firstsubmission.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.made_firstsubmission.R
import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import com.example.made_firstsubmission.BuildConfig
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlarmReceiver : BroadcastReceiver() {

    private lateinit var listItems : ArrayList<String>
    private lateinit var newMovieMessage : String
    companion object {
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "myChannel"
        const val MESSAGE = "message"
        const val TYPE = "type"
        const val TYPE_RELEASE = "release"

        const val ID_REPEATING_7 = 100
        const val ID_REPEATING_8 = 101
    }

    override fun onReceive(p0: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val type = intent.getStringExtra("type")
        val id = if(type.equals(TYPE,ignoreCase = true)) ID_REPEATING_7 else ID_REPEATING_8
        showNotification(p0,"Final Submission",message,id)
    }

    private fun showNotification(context: Context, title: String, message: String, notificationId : Int){
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_notifications_black_24dp))
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        mNotificationManager.notify(notificationId, notification)
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE, ignoreCase = true)) ID_REPEATING_7 else ID_REPEATING_8
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    fun setRepeatingAlarm(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(MESSAGE, "Hi, We Miss You So Much !")
        intent.putExtra(TYPE, TYPE)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING_7, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun setRepeatingAlarmNewMovie(context: Context){
        listItems = ArrayList()
        listItems.clear()

        var temp: String
        val date = getTodayDate().toStrings("yyyy-MM-dd")

        //get the new movies
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=${BuildConfig.TMDB_API_KEY}&primary_release_date.gte=$date&primary_release_date.lte=$date"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        temp = movie.getString("title")
                        listItems.add(temp)
                    }

                    newMovieMessage = "Today's New Movie : "
                    for (it in listItems){
                        newMovieMessage += "$it, "
                    }

                    //set the alarm
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.putExtra(MESSAGE, newMovieMessage)
                    intent.putExtra(TYPE, TYPE_RELEASE)

                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, 8)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)

                    val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING_8, intent, 0)
                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                    )
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun Date.toStrings(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getTodayDate(): Date {
        return Calendar.getInstance().time
    }
}