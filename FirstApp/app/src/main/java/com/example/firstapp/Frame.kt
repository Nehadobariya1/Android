package com.example.firstapp

import android.media.Rating
import android.os.Bundle
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Frame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_frame)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var rb : RatingBar = findViewById(R.id.ratingBar)
        var tv : TextView = findViewById(R.id.textView9)

        rb.setOnRatingBarChangeListener { ratingBar, fl, b ->
            tv.setText("Rating Value : $fl")
        }

        var sb: SeekBar = findViewById(R.id.seekBar)
        var tv2 : TextView = findViewById(R.id.textView11)

        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv2.setText("SeekBar Value = $p1")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        var sb2: SeekBar = findViewById(R.id.seekBar2)
        var tv3 : TextView = findViewById(R.id.textView12)

        sb2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv3.setText("SeekBar Value = $p1")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }
}