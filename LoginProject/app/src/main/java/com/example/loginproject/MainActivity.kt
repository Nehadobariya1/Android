package com.example.loginproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pbar : ProgressBar = findViewById(R.id.progressBar)

        Thread(Runnable {
            var count  = 0
            while(count<=100)
            {
                pbar.setProgress(count)
                count++
                Thread.sleep(100)
                pbar.secondaryProgress = count + 10
            }
            if(count >= 100)
            {
                var i =Intent(this,second::class.java)
                startActivity(i)
            }
        }).start()
    }
}