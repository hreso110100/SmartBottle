package sk.spacecode.smartbottle

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import sk.spacecode.smartbottle.dataClasses.DataTransfer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        var lastAmountDrinked = 0
        var currentDate = ""
    }

    private var deviceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(DashboardFragment())
        deviceId = intent.getStringExtra("device_mac")
        if(deviceId == null){
            deviceId = "20:13:10:17:10:29"
        }
        ReadData().execute()
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
                    currentDate = sdf.format(Date())
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
