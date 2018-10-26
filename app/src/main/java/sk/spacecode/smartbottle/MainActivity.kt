package sk.spacecode.smartbottle

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var deviceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(DashboardFragment.newInstance())
        deviceId = intent.getStringExtra("device_mac")
        ReadData().execute()

    }

    inner class ReadData : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {

            val bufferReader = BufferedReader(InputStreamReader(ConnectToBottleActivity.bluetoothSocket?.inputStream))

            var line = bufferReader.readLine()
            while (line != null) {
                Log.i("MAINDATA", line)
                line = bufferReader.readLine()
            }
            return null
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(DashboardFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_workout -> {
                loadFragment(StatisticsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                loadFragment(ProfileFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.dashboard_fragment_container, fragment).commit()


}
