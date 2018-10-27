package sk.spacecode.smartbottle

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var rootView: View
    private var moveLoadCoeficient = 3000 / 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val handler = Handler()
        val mTicker = object : Runnable {
            override fun run() {
                if (MainActivity.lastAmountDrinked.toFloat() == rootView.dashboard_circular_progress.progress * moveLoadCoeficient) {
                    rootView.dashboard_circular_progress.progress += 1F
                }

                if (rootView.dashboard_circular_progress.progress == 100F) {
                    rootView.dashboard_progress_text.visibility = View.GONE
                    rootView.dashboard_progress_text_done.visibility = View.VISIBLE
                }
                rootView.dashboard_last_amount_value.text = MainActivity.lastAmountDrinked.toString() + " ml"
                rootView.dashboard_last_time_value.text = MainActivity.lastTimeDrinked
                rootView.dashboard_last_temperature_value.text = MainActivity.lastTemperature + " °C"
                handler.postDelayed(this, 100)
            }
        }
        handler.postDelayed(mTicker, 100)
        return rootView
    }

}
