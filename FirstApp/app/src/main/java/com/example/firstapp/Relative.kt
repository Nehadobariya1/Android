package com.example.firstapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Relative : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_relative)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btn1 : Button = findViewById(R.id.button5)

        btn1.setOnClickListener {
            Toast.makeText(applicationContext,"Welcome to Relative Layout",Toast.LENGTH_LONG).show()
        }



        var imgbtn : ImageButton = findViewById(R.id.imageButton)

        imgbtn.setOnClickListener {
            Toast.makeText(applicationContext,"Image Button CLicked",Toast.LENGTH_LONG).show()
        }


        var tb : ToggleButton = findViewById(R.id.toggleButton)
        var imgview : ImageView = findViewById(R.id.imageView)

        tb.setOnClickListener {
            if(tb.text.equals("OFF"))
            {
                imgview.setImageResource(R.drawable.off)
            }
            else
            {
                imgview.setImageResource(R.drawable.onn)
            }
        }

        var fab : FloatingActionButton = findViewById(R.id.floatingActionButton2)

        fab.setOnClickListener {
            Toast.makeText(applicationContext,"Call",Toast.LENGTH_LONG).show()
        }

        var cb1 : CheckBox = findViewById(R.id.checkBox)
        var cb2 : CheckBox = findViewById(R.id.checkBox2)
        var cb3 : CheckBox = findViewById(R.id.checkBox3)
        var str : String
        var tv : TextView = findViewById(R.id.textView7)

        cb1.setOnClickListener {
            str = "JAVA : ${cb1.isChecked}\nKOTLIN : ${cb2.isChecked}\nPYTHON : ${cb3.isChecked}"
            tv.setText(str)
        }
        cb2.setOnClickListener {
            str = "JAVA : ${cb1.isChecked}\nKOTLIN : ${cb2.isChecked}\nPYTHON : ${cb3.isChecked}"
            tv.setText(str)
        }
        cb3.setOnClickListener {
            str = "JAVA : ${cb1.isChecked}\nKOTLIN : ${cb2.isChecked}\nPYTHON : ${cb3.isChecked}"
            tv.setText(str)
        }


        var rgroup : RadioGroup = findViewById(R.id.rgroup)
        var tv2 : TextView = findViewById(R.id.textView8)

        rgroup.setOnCheckedChangeListener { radioGroup, i ->
            var rb = findViewById<RadioButton>(i)
            if(rb != null) {
                tv2.setText(rb.text)
            }
        }

        var resetbtn : Button = findViewById(R.id.button6)

        resetbtn.setOnClickListener {
            rgroup.clearCheck()
            tv2.setText("Select Option")
        }
    }
}