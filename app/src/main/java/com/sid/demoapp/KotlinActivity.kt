package com.sid.demoapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fortislabs.commons.utils.ext.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.databinding.ActivityKotlinBinding
import com.sid.demoapp.databinding.ContentKotlinBinding
import com.sid.demoapp.kotllin.DummyDataSet
import com.sid.demoapp.kotllin.KotlinRecycleViewAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class KotlinActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityKotlinBinding
    private lateinit var contentRecyclerViewBinding: ContentKotlinBinding
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: KotlinRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinBinding.inflate(layoutInflater)
        val view = binding.root
        contentRecyclerViewBinding = ContentKotlinBinding.bind(view)
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        val viewModel: KotlinViewModel = viewModelProvider(viewModelFactory)
        title = viewModel.modelName

        viewManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
        viewAdapter = KotlinRecycleViewAdapter(DummyDataSet.dataSet)

        contentRecyclerViewBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        contentRecyclerViewBinding.actionSayHi.setOnClickListener { view ->
            Snackbar
                    .make(view, "Say 'Hi!' to Kotlin", Snackbar.LENGTH_SHORT)
                    .setAction("Hi, Kotlin", null).show()
        }

        contentRecyclerViewBinding.btnOne.setOnClickListener(this)
        contentRecyclerViewBinding.btnTwo.setOnClickListener(this)
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
