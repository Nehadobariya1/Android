package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btn1:Button = findViewById(R.id.button)

        btn1.setOnClickListener {
            var i = Intent(this,Constrainte::class.java)
            startActivity(i)
        }

        var btn2:Button = findViewById(R.id.button2)

        btn2.setOnClickListener {
            var i = Intent(this,Linear::class.java)
            startActivity(i)
        }

        var btn3:Button = findViewById(R.id.button3)

        btn3.setOnClickListener {
            var i = Intent(this,Relative::class.java)
            startActivity(i)
        }

        var btn4:Button = findViewById(R.id.button4)

        btn4.setOnClickListener {
            var i = Intent(this,Frame::class.java)
            startActivity(i)
        }

    }
}