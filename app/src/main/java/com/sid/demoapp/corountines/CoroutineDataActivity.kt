package com.sid.demoapp.corountines

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.sid.demoapp.R
import kotlinx.android.synthetic.main.activity_coroutine_data.*
import kotlinx.android.synthetic.main.content_coroutine_data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_data)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        action_get_coroutine_data.setOnClickListener {
            action_get_coroutine_data.isEnabled = false
            GlobalScope.launch {
                val dataRepository = DataRepository()
                val data = dataRepository.getDataFromRepository()

                withContext(Dispatchers.Main) {
                    coroutine_result.text = data
                    action_get_coroutine_data.isEnabled = true
                }
            }
        }

        action_reset_coroutine_data.setOnClickListener {
            coroutine_result.text = "No Data"
        }
    }

}
