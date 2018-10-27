package sk.spacecode.smartbottle

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val handler = Handler()
        val mTicker = object : Runnable {
            override fun run() {
                rootView.dashboard_last_amount_value.text = MainActivity.lastAmountDrinked.toString() + " ml"
                rootView.dashboard_last_time_value.text = MainActivity.currentDate
                handler.postDelayed(this, 100)
            }
        }
        handler.postDelayed(mTicker, 100)
        return rootView
    }

}
