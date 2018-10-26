package sk.spacecode.smartbottle

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        activity_welcome_openQrScanner.setOnClickListener {
            val intent = Intent(this, CodeScannerActivity::class.java)
            startActivity(intent)
        }

    }

}
