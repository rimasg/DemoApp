package com.sid.demoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sid.demoapp.databinding.ActivitySimpleModelBinding
import com.sid.demoapp.databinding.ContentSimpleModelBinding
import com.sid.demoapp.kotllin.KotlinRecycleViewAdapter
import com.sid.demoapp.model.SimpleViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SimpleModelActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivitySimpleModelBinding
    private lateinit var contentSimpleModelBinding: ContentSimpleModelBinding
    private lateinit var viewAdapter: KotlinRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleModelBinding.inflate(layoutInflater)
        contentSimpleModelBinding = binding.contentSimpleModel
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val viewModel = getViewModel<SimpleViewModel>()
        viewAdapter =   KotlinRecycleViewAdapter(viewModel.dummyData)
        contentSimpleModelBinding.simpleModelView.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

}
