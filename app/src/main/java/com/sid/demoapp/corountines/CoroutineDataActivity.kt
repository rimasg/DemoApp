package com.sid.demoapp.corountines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.databinding.ActivityCoroutineDataBinding
import com.sid.demoapp.databinding.ContentCoroutineDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutineDataBinding
    private lateinit var contentCoroutineDataBinding: ContentCoroutineDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineDataBinding.inflate(layoutInflater)
        val view = binding.root
        contentCoroutineDataBinding = ContentCoroutineDataBinding.bind(view)
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        contentCoroutineDataBinding.actionGetCoroutineData.setOnClickListener {
            contentCoroutineDataBinding.actionGetCoroutineData.isEnabled = false
            GlobalScope.launch {
                val dataRepository = DataRepository()
                val data = dataRepository.getDataFromRepository()

                withContext(Dispatchers.Main) {
                    contentCoroutineDataBinding.coroutineResult.text = data
                    contentCoroutineDataBinding.actionGetCoroutineData.isEnabled = true
                }
            }
        }

        contentCoroutineDataBinding.actionResetCoroutineData.setOnClickListener {
            contentCoroutineDataBinding.coroutineResult.text = "No Data"
        }
    }

}
