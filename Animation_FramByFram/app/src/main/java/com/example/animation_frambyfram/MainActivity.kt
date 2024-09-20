package com.example.animation_frambyfram

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ad=AnimationDrawable()
        var frame1 = resources.getDrawable(R.drawable.frame1,null)
        var frame2 = resources.getDrawable(R.drawable.frame2,null)
        var frame3 = resources.getDrawable(R.drawable.frame3,null)
        var frame4 = resources.getDrawable(R.drawable.frame4,null)
        var frame5 = resources.getDrawable(R.drawable.frame5,null)
        var frame6 = resources.getDrawable(R.drawable.frame6,null)
        var frame7 = resources.getDrawable(R.drawable.frame7,null)

        ad.addFrame(frame1,10)
        ad.addFrame(frame2,10)
        ad.addFrame(frame3,10)
        ad.addFrame(frame4,10)
        ad.addFrame(frame5,10)
        ad.addFrame(frame6,10)
        ad.addFrame(frame7,10)

        var iv=findViewById<ImageView>(R.id.imageView)
        iv.background=ad
        ad.start()
    }
}