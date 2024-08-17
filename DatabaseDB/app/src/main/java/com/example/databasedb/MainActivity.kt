package com.example.databasedb

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {


    lateinit var ed_sname : EditText
    lateinit var ed_sem : EditText
    lateinit var btn_insert : Button
    lateinit var btn_update :Button
    lateinit var btn_delete:Button
    lateinit var btn_clear  :Button
    lateinit var rs:Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ed_sname  =findViewById(R.id.ed_sname)
        ed_sem=findViewById(R.id.ed_sem)
        btn_insert=findViewById(R.id.insert)
        btn_update= findViewById(R.id.Update)
        btn_delete=findViewById(R.id.delete)
        btn_clear=findViewById(R.id.Clear)


        var helper=MyDBHelper(applicationContext)
        var db=helper.writableDatabase
        Toast.makeText(applicationContext,"Db and Table Cretaed", Toast.LENGTH_LONG).show()

        rs=db.rawQuery("SELECT SID _id,SNAME, SEM FROM STUDENT",null)

        if(rs.moveToFirst())
        {
            ed_sname.setText(rs.getString(1))
            ed_sem.setText(rs.getString(2))
        }


        btn_insert.setOnClickListener {

            var cv = ContentValues()
            cv.put("SNAME",ed_sname.text.toString())
            cv.put("SEM",ed_sem.text.toString())

            db.insert("STUDENT",null,cv)
//            Toast.makeText(applicationContext,"inserted", Toast.LENGTH_LONG).show()
            showMessage("Record Inserted")
            clear()
        }
        btn_update.setOnClickListener {
            var cv = ContentValues()
            cv.put("SNAME",ed_sname.text.toString())
            cv.put("SEM",ed_sem.text.toString())

            db.update("STUDENT",cv,"SID=?", arrayOf(rs.getString(0)))
            rs=db.rawQuery("SELECT SID _id,SNAME, SEM FROM STUDENT",null)
            showMessage("Record Updated Successfully!")
            clear()

        }
        btn_delete.setOnClickListener {
            db.delete("STUDENT","SID=?", arrayOf(rs.getString(0)))
            rs=db.rawQuery("SELECT SID _id,SNAME, SEM FROM STUDENT",null)
            showMessage("Record Deleted Successfully!")
            clear()
        }
        btn_clear.setOnClickListener {
            clear()
        }
    }

    private fun showMessage(s: String) {
        AlertDialog.Builder(this)
        .setTitle("SUccess!")
        .setMessage(s)
        .setPositiveButton("OK",DialogInterface.OnClickListener { dialogInterface, i ->
            if(rs.moveToFirst())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))
            }
            else
            {
                Toast.makeText(applicationContext,"Data not found!!",Toast.LENGTH_LONG).show()
            }
        }).show()

    }

    private fun clear() {
       ed_sname.setText("")
        ed_sem.setText("")
        ed_sname.requestFocus()
    }


}