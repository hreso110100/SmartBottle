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

        val bottleId = intent.getStringExtra("qr_result")

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        activity_register_bottle_bottleId.text = bottleId


        //generating QR code
//        val myBitmap = QRCode.from(bottleId.toString()).bitmap()
//        activity_register_bottle_qrImage.setImageBitmap(myBitmap)


        activity_register_bottle_firebase.setOnClickListener {

            var password = activity_register_bottle_bottlePassword.text

            if(password.isNotEmpty()){
                val bottle = Bottle(bottleId, password.toString())
                val intent = Intent(this, ConnectToBottleActivity::class.java)
                intent.putExtra("bottle_id", bottleId.toString())
                startActivity(intent)

//                mDatabase!!.child(bottleId).setValue(bottle).addOnCompleteListener(
//                        OnCompleteListener { task ->
//                            when {
//                                task.isSuccessful -> {
//
//                                }
//                                else -> {
//                                    Toast.makeText(this, "Push unsucessfull." , Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                        })
            }else{
                Toast.makeText(this, "Please use longer password.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
