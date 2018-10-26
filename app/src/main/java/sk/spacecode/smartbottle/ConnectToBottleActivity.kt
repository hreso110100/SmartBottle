package sk.spacecode.smartbottle

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.IOException
import java.util.*

class ConnectToBottleActivity : AppCompatActivity() {

    private val bluetoothRequestCode = 1
    private var bluetoothSocket: BluetoothSocket? = null
    private val myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var isDeviceConnected = false
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val logConstantClass = "ConnectToBottleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_to_bottle)

        // Checking if device has Bluetooth

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            finish()
        }

        // Asking to enable Bluetooth, if it is off

        if (!bluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, bluetoothRequestCode)
        }
        ConnectToArduino().execute()
    }

    inner class ConnectToArduino : AsyncTask<Void, Void, Void>() {
        private var connectSuccess = true

        override fun onPreExecute() {
            Log.i(logConstantClass, "CONNECTING...WAIT")
        }

        override fun doInBackground(vararg devices: Void): Void? {
            try {
                val device = this@ConnectToBottleActivity.bluetoothAdapter?.getRemoteDevice("20:13:10:17:10:29")
                device?.createBond()

                if (device?.bondState == BluetoothDevice.BOND_BONDED) {
                    Log.i(logConstantClass, "CONNECTING TO ${device.address}")

                    if (bluetoothSocket == null || !isDeviceConnected) {
                        bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                        bluetoothSocket!!.connect()
                    }
                }
            } catch (e: IOException) {
                connectSuccess = false
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            if (!connectSuccess) {
                Log.i(logConstantClass, "Connection Failed")
                finish()
            } else {
                Log.i(logConstantClass, "Connection Success")
                isDeviceConnected = true
            }
        }
    }
}
