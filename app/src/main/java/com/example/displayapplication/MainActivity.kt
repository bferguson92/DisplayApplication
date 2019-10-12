package com.example.displayapplication

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val authority =
        "contents://com.example.entryapplication..provider.CustomerProvider/customer"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        getCurrentCustomers()
    }

    private fun getCurrentCustomers() {
        val uri = Uri.parse(authority)
        var cursor: Cursor?
        val stringBuilder = StringBuilder()

        try {
            Log.d("TAG_TRY", "TRY")
            cursor = contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )

            cursor?.let { myValues ->
                if (myValues.count == 0) {
                    display_customers_text_view.text = "NO CUSTOMERS"
                } else {
                    myValues.moveToFirst()
                    while (myValues.moveToNext()) {
                        stringBuilder.append(
                            "${myValues.getString(myValues.getColumnIndex("customer_name"))} " +
                                    "| ${myValues.getString(myValues.getColumnIndex("customer_relation"))}"
                        )
                    }
                }
                myValues.close()
            }?: {
                display_customers_text_view.text = "EMPTY"
            }()

        } catch (throwable: Throwable) {
            Log.d("TAG_ERROR", throwable.toString() + "anything")
        }
    }
}
