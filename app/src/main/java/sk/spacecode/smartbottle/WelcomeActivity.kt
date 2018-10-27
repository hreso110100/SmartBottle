package sk.spacecode.smartbottle

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    companion object {
        var data = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentToolbar()
        setContentView(R.layout.activity_welcome)




//        Log.e("","In addDrinkWather()")
////        Toast.makeText(this, "In addDrinkWather()", Toast.LENGTH_SHORT).show()
//
//        var mDatabase: DatabaseReference?
//        mDatabase = FirebaseDatabase.getInstance().reference
//
//        var value = 0
//
//        val rootRef = mDatabase!!.child("deviceId").child("drinkedWather")

//        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Log.e("","--------------------")
//                Log.e("",p0!!.message)
//                Toast.makeText(this@WelcomeActivity, p0!!.message, Toast.LENGTH_SHORT).show();
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val children = p0!!.children
//                children.forEach { it ->
//                    Log.e("","--------------------")
//                    Log.e("",it.toString())
//                    WelcomeActivity.data = it.toString().toInt()
//                    Toast.makeText(this@WelcomeActivity, it.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        })


//        Toast.makeText(this, WelcomeActivity.data, Toast.LENGTH_SHORT).show()


        activity_welcome_buttonScan.setOnClickListener { startActivity(Intent(this@WelcomeActivity, CodeScannerActivity::class.java)) }
        activity_welcome_iconImage.setOnClickListener { startActivity(Intent(this@WelcomeActivity, CodeScannerActivity::class.java)) }

    }

    private fun transparentToolbar() {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

}
