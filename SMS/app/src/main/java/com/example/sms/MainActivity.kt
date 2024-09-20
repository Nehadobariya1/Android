package com.example.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var phone : TextView
    lateinit var smsBody:TextView
    lateinit var sendBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phone=findViewById(R.id.tv1_phone)
        smsBody=findViewById(R.id.tv2_smsbody)
        sendBtn=findViewById(R.id.button)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS,android.Manifest.permission.SEND_SMS),111)
        }
        else{
            receiveSMS()
        }
        sendBtn.setOnClickListener{
            var sms = SmsManager.getDefault()
            sms.sendTextMessage(phone.text.toString(),"ME",smsBody.text.toString(),null,null)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            receiveSMS()
        }
    }

    private fun receiveSMS() {

        var br=object : BroadcastReceiver()
        {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                {
                    for(sms in Telephony.Sms.Intents.getMessagesFromIntent(p1))
                    {
                        phone.setText((sms.originatingAddress))
                        smsBody.setText(sms.displayMessageBody)
                    }
                }
            }

        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}