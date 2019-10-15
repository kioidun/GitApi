package com.example.gitapi.activity.MainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.gitapi.activity.MainCallBack.MainCallBack
import com.example.gitapi.R

class MainActivity : AppCompatActivity() {

    private lateinit var mlogin: Button
    private lateinit var minputUserName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mlogin = findViewById(R.id.login)
        minputUserName = findViewById(R.id.input_username)

        mlogin.setOnClickListener {
            getUser()
        }
    }

    fun getUser() {
        val i = Intent(this@MainActivity, MainCallBack::class.java)
        i.putExtra("STRING_I_NEED", minputUserName.text.toString())
        startActivity(i)

    }
}
