package com.sid.demoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.kotllin.KotlinRecycleViewAdapter
import com.sid.demoapp.model.SimpleViewModel
import kotlinx.android.synthetic.main.activity_simple_model.*
import kotlinx.android.synthetic.main.content_simple_model.*
import org.koin.android.architecture.ext.getViewModel

class SimpleModelActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private lateinit var viewAdapter: KotlinRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_model)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val viewModel = getViewModel<SimpleViewModel>()
        viewAdapter =   KotlinRecycleViewAdapter(viewModel.dummyData)
        simple_model_view.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

}
