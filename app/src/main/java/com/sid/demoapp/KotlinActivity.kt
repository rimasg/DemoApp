package com.sid.demoapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin.*
import kotlinx.android.synthetic.main.content_kotlin.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        action_say_hi.setOnClickListener { view ->
            Snackbar
                    .make(view, "Say 'Hi!' to Kotlin", Snackbar.LENGTH_SHORT)
                    .setAction("Hi, Kotlin", null).show()
        }
    }
}
