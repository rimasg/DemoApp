package com.sid.demoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_flip_view.*
import kotlinx.android.synthetic.main.content_flip_view.*

class FlipViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip_view)
        setSupportActionBar(toolbar)

        action_flip.setOnClickListener { flip_view.flip() }
    }
}
