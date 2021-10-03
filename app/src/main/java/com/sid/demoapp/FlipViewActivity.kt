package com.sid.demoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sid.demoapp.databinding.ActivityFlipViewBinding
import com.sid.demoapp.databinding.ContentFlipViewBinding

class FlipViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlipViewBinding
    private lateinit var contentFlipViewBinding: ContentFlipViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlipViewBinding.inflate(layoutInflater)
        contentFlipViewBinding = binding.contentFlipView
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        contentFlipViewBinding.actionFlip.setOnClickListener { contentFlipViewBinding.flipView.flip() }
    }
}
