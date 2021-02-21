package com.sid.demoapp.collapsingtoolbar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.sid.demoapp.databinding.ActivityCollapsingToolbarBinding

class CollapsingToolbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.goEdgeToEdge()

        val binding = ActivityCollapsingToolbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listener =
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
                binding.motionLayout.progress = seekPosition
            }
        binding.appbarLayout.addOnOffsetChangedListener(listener)
    }

    private fun Window.goEdgeToEdge() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}