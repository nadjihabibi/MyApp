package com.nadji.myapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nadji.myapp.common.utils.Constant
import com.nadji.myapp.common.utils.showAlert
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.databinding.ActivityTransHistoryBinding
import com.nadji.myapp.presentation.adapter.AdapterHistory
import com.nadji.myapp.presentation.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransHistoryBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.getHistoryResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            initHistory(it.data)
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }

        initView()

    }

    private fun initView() {
        with(binding) {
            ivBack.setOnClickListener {
                super.onBackPressed()
            }
        }
    }

    private fun initHistory(data: List<HistoryEntity>?) {
        if (!data.isNullOrEmpty()) {
            val adapter = AdapterHistory(data, object : AdapterHistory.OnClickListener {
                override fun detail(item: HistoryEntity) {
                    showAlert(item.merchantName.toString(), "${item.transId}")
                }
            })
            binding.rvStore.adapter = adapter
        } else {
            showAlert(Constant.APP_NAME, "History Kosong")
        }
    }
}