package com.example.databasedb

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {


    lateinit var ed_sname : EditText
    lateinit var ed_sem : EditText
    lateinit var btn_insert : Button
    lateinit var btn_update :Button
    lateinit var btn_delete:Button
    lateinit var btn_clear  :Button
    lateinit var next_btn:Button
    lateinit var prev_btn:Button
    lateinit var first_btn:Button
    lateinit var last_btn:Button
    lateinit var btn_showall  :Button
    lateinit var listview : ListView
    lateinit var search_view :SearchView
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
        next_btn=findViewById(R.id.next)
        prev_btn=findViewById(R.id.prev)
        first_btn=findViewById(R.id.first)
        last_btn=findViewById(R.id.last)
        btn_showall = findViewById(R.id.search_btn)
        listview = findViewById(R.id.listview)
        search_view = findViewById(R.id.search_view)

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
            rs=db.rawQuery("SELECT SID _id,SNAME, SEM FROM STUDENT",null)
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
        next_btn.setOnClickListener{
            if(rs.moveToNext())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))

            }else if(rs.moveToFirst())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))
            }else{
                Toast.makeText(applicationContext,"Data not found!!", Toast.LENGTH_LONG).show()
            }
        }
        prev_btn.setOnClickListener{
            if(rs.moveToPrevious())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))

            }else if(rs.moveToLast())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))
            }else{
                Toast.makeText(applicationContext,"Data not found!!", Toast.LENGTH_LONG).show()
            }
        }
        first_btn.setOnClickListener{
            if(rs.moveToFirst())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))

            }else{
                Toast.makeText(applicationContext,"Data not found!!", Toast.LENGTH_LONG).show()
            }
        }
        last_btn.setOnClickListener{
            if(rs.moveToLast())
            {
                ed_sname.setText(rs.getString(1))
                ed_sem.setText(rs.getString(2))

            }else{
                Toast.makeText(applicationContext,"Data not found!!", Toast.LENGTH_LONG).show()
            }
        }
        btn_showall.setOnClickListener {
            var adapter = SimpleCursorAdapter(applicationContext,R.layout.custome_layout,rs,
                arrayOf("SNAME","SEM"),
                intArrayOf(R.id.text1,R.id.text2))
            listview.adapter = adapter

            search_view.queryHint = "Search among ${rs.count} Records"
            search_view.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                  return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    rs=db.rawQuery("SELECT SID _id,SNAME,SEM FROM STUDENT WHERE SNAME LIKE '%${p0}%' OR SEM LIKE '%${p0}%'",null)
                    adapter.changeCursor(rs)

                   return false
                }

            })

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