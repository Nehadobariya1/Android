package com.example.firstapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.MultiAutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Linear : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_linear)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var mail : EditText = findViewById(R.id.editTextTextEmailAddress)

        mail.addTextChangedListener(object:TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(p0).matches())
                {
                    mail.setError("Invalid Email")
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        var activity : AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        var city = arrayOf("Rajkot","surat","jamnagar")
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,city)

        activity.setAdapter(adapter)


        var mactvSkill : MultiAutoCompleteTextView = findViewById(R.id.mactvskills)
        var skills = arrayOf("Web Designing","Web Dev","SEO")
        var adapter1 = ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,skills)

        mactvSkill.setAdapter(adapter1)
        mactvSkill.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
}