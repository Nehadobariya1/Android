//
//package com.example.contactprovider
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.database.Cursor
//import android.net.Uri
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.provider.ContactsContract
//import android.telephony.TelephonyManager
//import android.widget.ListView
//import android.widget.SearchView
//import android.widget.SimpleCursorAdapter
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//
//class MainActivity : AppCompatActivity() {
//
//    var cols = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone._ID)
//    private var phoneNumber: String? = null
//      private var rs: Cursor? = null
//    private lateinit var adapter: SimpleCursorAdapter
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),11)
//
//        }else{
//            readContacts()
//        }
//
//        val searchView:SearchView=findViewById(R.id.searchView)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                filterContacts(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterContacts(newText)
//                return true
//            }
//        })
//
//
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 11 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            readContacts()
//        }
//        else if (requestCode == 12 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            makePhoneCall(phoneNumber)
//        }
//
//    }
//
//
//    private fun readContacts() {
//
//         var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,cols,null,null,null)!!
//        if (rs != null){
//
//            var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,cols,
//                intArrayOf(android.R.id.text1,android.R.id.text2))
//            var listView :ListView = findViewById(R.id.list_item)
//            listView.adapter = adapter
////
//            listView.setOnItemClickListener { adapterView, view, i, l ->
//
//                rs.moveToPosition(i)
//             //Toast.makeText(applicationContext,"$i",Toast.LENGTH_LONG).show()
//               //phoneNumber = rs.getString(i)
//                val phoneNumber = rs?.getString(rs?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) ?: -1)
//                makePhoneCall(phoneNumber)
//
//                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this,
//                       arrayOf(android.Manifest.permission.CALL_PHONE),
//                       12)
//                } else {
//
//                    makePhoneCall(phoneNumber) }
//            }
//
//        }else{
//
//        }
//    }
//
//    private fun makePhoneCall(phoneNumber: String?) {
//        val intent = Intent(Intent.ACTION_CALL).apply {
//            data = Uri.parse("tel:$phoneNumber")
//        }
//        startActivity(intent)
//    }
//private fun filterContacts(query: String?) {
//    val selection = if (query.isNullOrEmpty()) null else "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
//    val selectionArgs = if (query.isNullOrEmpty()) null else arrayOf("%$query%")
//    val newCursor = contentResolver.query(
//        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//        cols,
//        selection,
//        selectionArgs,
//        null
//    )
//    adapter.changeCursor(newCursor)
//    rs?.close() // Close the old cursor to avoid memory leaks
//    rs = newCursor
//    }
//
//}
//
//
//
//




//class MainActivity : AppCompatActivity() {
//
//    private val cols = arrayOf(
//        ContactsContract.CommonDataKinds.Phone._ID,
//        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//        ContactsContract.CommonDataKinds.Phone.NUMBER
//    )
//
//    private var phoneNumber: String? = null
//    private var rs: Cursor? = null
//    private lateinit var adapter: SimpleCursorAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 11)
//        } else {
//            readContacts()
//        }
//
//        val searchView: SearchView = findViewById(R.id.searchView)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                filterContacts(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterContacts(newText)
//                return true
//            }
//        })
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 11 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            readContacts()
//        } else if (requestCode == 12 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            phoneNumber?.let { makePhoneCall(it) }
//        }
//    }
//
//    @SuppressLint("Range")
//    private fun readContacts() {
//        rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols, null, null, null)
//        rs?.let {
//            adapter = SimpleCursorAdapter(
//                this,
//                android.R.layout.simple_expandable_list_item_2,
//                it,
//                arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER),
//                intArrayOf(android.R.id.text1, android.R.id.text2),
//                0
//            )
//            val listView: ListView = findViewById(R.id.list_item)
//            listView.adapter = adapter
//
//            listView.setOnItemClickListener { _, _, position, _ ->
//                it.moveToPosition(position)
//                phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 12)
//                } else {
//                    phoneNumber?.let { number -> makePhoneCall(number) }
//                }
//            }
//        }
//    }
//
//    private fun filterContacts(query: String?) {
//        val selection = if (query.isNullOrEmpty()) null else "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
//        val selectionArgs = if (query.isNullOrEmpty()) null else arrayOf("%$query%")
//        val newCursor = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            cols,
//            selection,
//            selectionArgs,
//            null
//        )
//        adapter.changeCursor(newCursor)
//        rs?.close() // Close the old cursor to avoid memory leaks
//        rs = newCursor
//    }
//
//    private fun makePhoneCall(phoneNumber: String) {
//        val intent = Intent(Intent.ACTION_CALL).apply {
//            data = Uri.parse("tel:$phoneNumber")
//        }
//        startActivity(intent)
//    }
//}



package com.example.contactprovider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private var cursor: Cursor? = null
    private lateinit var adapter: SimpleCursorAdapter
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_item)

        // Request permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            readContacts()
        }

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterContacts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterContacts(newText)
                return true
            }
        })
         val fbutton : FloatingActionButton = findViewById(R.id.floatingActionButton)
        fbutton.setOnClickListener {

            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE

            }
            startActivityForResult(intent, 12)
        }


    }

    @SuppressLint("Range")
    private fun readContacts() {
        val cols = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID
        )

        cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, cols, null, null, null)
        cursor?.let {
            adapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_expandable_list_item_2,
                it,
                cols,
                intArrayOf(android.R.id.text1, android.R.id.text2),
                0
            )
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ ->
                cursor?.moveToPosition(position)
                val phoneNumber = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) ?: -1)
                makeCall(phoneNumber)
            }
        }
    }

    private fun filterContacts(query: String?) {
        val selection = if (query.isNullOrEmpty()) null else "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
        val selectionArgs = if (query.isNullOrEmpty()) null else arrayOf("%$query%")
        cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID
            ),
            selection,
            selectionArgs,
            null
        )
        adapter.changeCursor(cursor)
    }

    private fun makeCall(phoneNumber: String?) {
        if (!phoneNumber.isNullOrEmpty()) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent)
            } else {
                // Handle permission not granted scenario
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                readContacts()
            } else {
                // Permissions denied
                Toast.makeText(this, "Permissions are required to access contacts and make calls.", Toast.LENGTH_LONG).show()
            }
        }
    }


}
