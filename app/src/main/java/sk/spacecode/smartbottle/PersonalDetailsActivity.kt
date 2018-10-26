package sk.spacecode.smartbottle

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_personal_details.*

class PersonalDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)


        activity_personal_details_submit.setOnClickListener {
            val intent = Intent(this, MainActivity()::class.java)
            startActivity(intent)

        }
    }
}
