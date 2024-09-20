package com.example.gradiant_project

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setContentView(Test(applicationContext))
    }

    class Test(context: Context):View(context) {

        var p = Paint(Paint.ANTI_ALIAS_FLAG)

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            //linearGradient
            var lg = LinearGradient(50f,20f,150f,150f,Color.BLUE,Color.MAGENTA,Shader.TileMode.MIRROR)
            p.shader=lg
            canvas?.drawCircle(250f,250f,200f,p)

            //RadialGradient
            var rg = RadialGradient(200f,600f,200f,Color.BLUE,Color.MAGENTA,Shader.TileMode.MIRROR)
            p.shader=rg
            canvas?.drawCircle(250f,750f,200f,p)

            //Sweep Gradient
            var sg=SweepGradient(250f,1250f, intArrayOf(Color.DKGRAY,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA),null)
            p.shader=sg
            canvas?.drawCircle(250f,1250f,200f,p)

        }
    }
}