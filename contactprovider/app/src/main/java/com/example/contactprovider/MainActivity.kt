



package com.example.contactprovider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import  android.widget.AdapterView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var cursor: Cursor? = null
    private lateinit var adapter: SimpleCursorAdapter
    private lateinit var listView: ListView
    private var selectedContactNumber: String? = null
    private var selectedContactId: String? = null

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

        val fbutton: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fbutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
            }
            startActivityForResult(intent, 12)
        }

        registerForContextMenu(listView)
    }


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
                selectedContactNumber = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) ?: -1)
                selectedContactId = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID) ?: -1)

                // Make the call immediately when an item is clicked
                makeCall(selectedContactNumber)
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

            // Check for CALL_PHONE permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent)
            } else {
                // Request permission if not granted
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            }
        } else {
            Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(101,1001,1,"Add Contact")
        menu?.add(102,1002,2,"Edit")
        menu?.add(103,1003,3,"Setting")


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            1001-> {
                val intent = Intent(Intent.ACTION_INSERT).apply {
                    type = ContactsContract.RawContacts.CONTENT_TYPE
                }
                startActivityForResult(intent, 12)
            }
            1002->Toast.makeText(applicationContext,"Edit",Toast.LENGTH_LONG).show()
            1003->{
                val intent = Intent(this,ContactDetailActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle("Choose an action")
        menu?.add(0, 1, 0, "Call")
        menu?.add(0, 2, 1, "Send SMS")
        menu?.add(0, 3, 2, "WhatsApp")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        cursor?.moveToPosition(info.position)
        selectedContactNumber = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) ?: -1)
        selectedContactId = cursor?.getString(cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID) ?: -1)

        return when (item.itemId) {
            1 -> {
                makeCall(selectedContactNumber)
                true
            }
            2 -> {
                val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:${selectedContactNumber}")
                }
                startActivity(smsIntent)
                true
            }
            3 -> {
                selectedContactNumber?.let {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://wa.me/$it")
                    }
                    startActivity(intent)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }




}
