package sk.spacecode.smartbottle

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_personal_details.*
import org.jetbrains.anko.indeterminateProgressDialog
import sk.spacecode.smartbottle.dataClasses.Person

class PersonalDetailsActivity : AppCompatActivity() {

    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)

        val bottleId = intent.getStringExtra("device_mac")
        mDatabase = FirebaseDatabase.getInstance().reference

        activity_personal_details_submit.setOnClickListener {
            val name = activity_register_bottle_bottlePassword.text
            val weight = activity_personal_details_name.text

            if (name.isNotEmpty() && weight.isNotEmpty()) {
                val personData = Person(name.toString(), weight.toString())

                val dialog = indeterminateProgressDialog("Wait  a bit ...")

                dialog.show()
                mDatabase!!.child(bottleId).child("personalData").setValue(personData).addOnCompleteListener { task ->
                    when {
                        task.isSuccessful -> {
                            val intent = Intent(this, MainActivity()::class.java)
                            intent.putExtra("device_mac", bottleId)
                            startActivity(intent)
                            dialog.hide()
                        }
                        else -> {
                            Toast.makeText(this, "Push unsucessfull.", Toast.LENGTH_SHORT).show()
                            dialog.hide()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill fields above.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
