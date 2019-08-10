package com.sid.demoapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortislabs.commons.utils.ext.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.kotllin.DummyDataSet
import com.sid.demoapp.kotllin.KotlinRecycleViewAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin.*
import kotlinx.android.synthetic.main.content_kotlin.*
import javax.inject.Inject

class KotlinActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var viewAdapter: KotlinRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        setSupportActionBar(toolbar)

        val viewModel: KotlinViewModel = viewModelProvider(viewModelFactory)
        title = viewModel.modelName

        viewManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
        viewAdapter = KotlinRecycleViewAdapter(DummyDataSet.dataSet)

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        action_say_hi.setOnClickListener { view ->
            Snackbar
                    .make(view, "Say 'Hi!' to Kotlin", Snackbar.LENGTH_SHORT)
                    .setAction("Hi, Kotlin", null).show()
        }

        btn_one.setOnClickListener(this)
        btn_two.setOnClickListener(this)
    }

    private fun displayMessage(view: View, msg: String) {
        Snackbar
                .make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction(msg, null).show()
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.btn_one -> displayMessage(view, "Button One clicked")
            R.id.btn_two -> displayMessage(view, "Button Two clicked")
        }
    }

}
