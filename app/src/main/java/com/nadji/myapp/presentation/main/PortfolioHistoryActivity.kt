package com.nadji.myapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadji.myapp.data.network.model.DataItems
import com.nadji.myapp.databinding.ActivityPortfolioHistoryBinding
import com.nadji.myapp.presentation.adapter.PortofolioAdapter

class PortfolioHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPortfolioHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataReceived = intent.getSerializableExtra("dataPort") as? List<DataItems>

        if (dataReceived != null) {
//            val layoutManager = LinearLayoutManager(this)
            val spanCount = 2
            val layoutManager = GridLayoutManager(this, spanCount)
            binding.recyclerView.layoutManager = layoutManager

            val adapter = PortofolioAdapter(dataReceived)
            binding.recyclerView.adapter = adapter
        } else {
        }

    }
}