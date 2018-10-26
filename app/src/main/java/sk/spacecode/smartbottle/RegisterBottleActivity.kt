package sk.spacecode.smartbottle

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_bottle.*
import sk.spacecode.smartbottle.dataClasses.Bottle


class RegisterBottleActivity : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_bottle)

        val bottleId = intent.getStringExtra("device_mac")

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        activity_register_bottle_bottleId.text = bottleId

        activity_register_bottle_firebase.setOnClickListener {

            val password = activity_register_bottle_bottlePassword.text

            if (password.isNotEmpty()) {
                val intent = Intent(this, PersonalDetailsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please use longer password.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
