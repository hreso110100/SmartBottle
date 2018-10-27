package sk.spacecode.smartbottle

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.AsyncTask
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import sk.spacecode.smartbottle.dataClasses.DataTransfer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var counter = 0

    companion object {
        var lastAmountDrinked = 0
        var lastTimeDrinked = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(DashboardFragment())
        ReadData().execute()

        val handler = Handler()
        val mTicker = object : Runnable {
            override fun run() {

                counter++
                Log.i("MAIN", counter.toString())

                if (counter == 30) {
                    showNotification()
                    counter = 0
                }

                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(mTicker, 1000)

    }

    fun showNotification() {
        val mBuilder = NotificationCompat.Builder(this, 1.toString())
            .setSmallIcon(R.drawable.ic_warning_black_24dp)
            .setContentTitle("Take a shot of water")
            .setContentText("Drinking water in regular period makes you healthier.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(1, mBuilder.build())
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val name = 1.toString()
            val descriptionText = "test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(1.toString(), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    inner class ReadData : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {

            val bufferReader = BufferedReader(InputStreamReader(ConnectToBottleActivity.bluetoothSocket?.inputStream))

            var line = bufferReader.readLine()
            val gson = Gson()
            while (line != null) {
                val actualAmountOfWater = gson.fromJson(line, DataTransfer::class.java)
                if (actualAmountOfWater.objem.toInt() != 0) {
                    lastAmountDrinked = actualAmountOfWater.objem.toInt()
                    val sdf = SimpleDateFormat("dd/M hh:mm:ss", Locale.GERMAN)
                    lastTimeDrinked = sdf.format(Date())
                }
                line = bufferReader.readLine()
            }

            return null
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(DashboardFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_workout -> {
                loadFragment(StatisticsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                loadFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.dashboard_fragment_container, fragment).commit()


}
