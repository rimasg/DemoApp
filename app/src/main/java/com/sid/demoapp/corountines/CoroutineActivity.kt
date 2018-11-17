package com.sid.demoapp.corountines

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.sid.demoapp.R

class CoroutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        val rootLayout: ConstraintLayout = findViewById(R.id.rootLayout)
        val title: TextView = findViewById(R.id.title)
        val spinner: ProgressBar = findViewById(R.id.spinner)

        val database = getDatabase(this)
        val repository = TitleRepository(CoroutineNetworkImpl, database.titleDao)
        val viewModel = ViewModelProviders
                .of(this, CoroutineViewModel.FACTORY(repository))
                .get(CoroutineViewModel::class.java)

        rootLayout.setOnClickListener {
            viewModel.onViewModelClicked()
        }

        viewModel.title.observe( this, Observer { value ->
            value?.let {
                title.text = it
            }
        })

        viewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                spinner.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(rootLayout, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBackShown()
            }
        })
    }
}
