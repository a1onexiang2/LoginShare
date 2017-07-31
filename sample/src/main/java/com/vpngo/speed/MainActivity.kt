package com.vpngo.speed

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Neil Zheng on 2017/7/28.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        action_kotlin.setOnClickListener {
            startActivity(Intent(this@MainActivity, KotlinActivity::class.java))
        }
        action_java.setOnClickListener {
            startActivity(Intent(this@MainActivity, JavaActivity::class.java))
        }
        title = "LoginShare Demo"
    }
}