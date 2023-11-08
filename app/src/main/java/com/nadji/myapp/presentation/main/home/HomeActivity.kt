package com.nadji.myapp.presentation.main.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.nadji.myapp.R
import com.nadji.myapp.common.utils.Constant
import com.nadji.myapp.common.utils.showAlert
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.DataItems
import com.nadji.myapp.data.network.model.PromoEntity
import com.nadji.myapp.data.network.model.PromoResponse
import com.nadji.myapp.databinding.ActivityHomeBinding
import com.nadji.myapp.presentation.adapter.AdapterPromo
import com.nadji.myapp.presentation.main.DetailPromoActivity
import com.nadji.myapp.presentation.main.PortfolioHistoryActivity
import com.nadji.myapp.presentation.main.ScannerActivity
import com.nadji.myapp.presentation.main.TransHistoryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var promoAdapter: AdapterPromo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getUserById("1")
        promoAdapter = AdapterPromo()
        initView()
    }

    private fun initView() {
        onView()
        onClick()

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.getUserByIdResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            initUser(it.data)
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.getUserResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            showData(it.data)
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }
        viewModel.promo()

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.promoResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            viewModel.deletePromo()
                            val promoResponse: PromoResponse? = it.data
                            if (promoResponse?.data != null) {
                                promoResponse.data.forEach { promoDataItem ->
                                    promoDataItem?.attributes?.let { promoEntity ->
                                        viewModel.insertPromo(promoEntity)
                                    }
                                }
                            }
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.getPromoResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            showDataPromo(it.data)
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                        }

                        else -> {}
                    }
                }
            }
        }

        viewModel.readPortofolioDataFromAssets(this)
        viewModel.viewModelScope.launch {
            viewModel.fetchPortofolioDataFromAssets(assets)
        }

        viewModel.getPortofolioData().observe(this) { portofolioResponse ->
            if (portofolioResponse != null) {
                val chartData =
                    portofolioResponse.portofolioResponse?.find { it?.type == "donutChart" }
                if (chartData != null) {
                    val donutChartData = chartData.data
                    showDonutChart(donutChartData)
                }
            }
        }

        showLineChart()

    }

    private fun showLineChart() {
        val lineChart = binding.qtyChart

        // Data dari JSON
        val data = listOf(3, 7, 8, 10, 5, 10, 1, 3, 5, 10, 7, 7)

        val entries = ArrayList<Entry>()

        // Mengisi data grafik
        for ((index, value) in data.withIndex()) {
            entries.add(Entry(index.toFloat(), value.toFloat()))
        }

        val dataSet = LineDataSet(entries, "Bulan")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        lineChart.description.isEnabled = false
        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.axisRight.isEnabled = false

        lineChart.invalidate()
    }

    private fun showDonutChart(donutChartData: List<DataItems?>?) {
        val pieChart = binding.chart

        val pieEntries = ArrayList<PieEntry>()

        donutChartData?.forEachIndexed { index, dataItem ->
            val nominal = dataItem?.nominal ?: 0
            pieEntries.add(PieEntry(nominal.toFloat(), dataItem?.label ?: "Label $index"))
        }

        val dataSet = PieDataSet(pieEntries, "Donut Chart")
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data

        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.holeRadius = 30f
        pieChart.transparentCircleRadius = 0f
        pieChart.setUsePercentValues(true)
        pieChart.centerText = "Donut Chart"
        pieChart.setEntryLabelColor(Color.BLACK)


        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    val dataIndex = e.x.toInt()
                    val dataItem = donutChartData?.get(dataIndex)
                    if (dataItem != null) {
                        val label = dataItem.label
                        val dataToSend = donutChartData.filter { it?.label == label }
                        val intent = Intent(this@HomeActivity, PortfolioHistoryActivity::class.java)
                        intent.putExtra("dataPort", ArrayList(dataToSend))
                        startActivity(intent)
                    }
                }
            }

            override fun onNothingSelected() {}
        })

        pieChart.invalidate()
    }

    private fun showDataPromo(data: List<PromoEntity>?) {
        promoAdapter.setList(data)
        binding.rvPromo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPromo.adapter = promoAdapter
        binding.rvPromo.setHasFixedSize(true)

        promoAdapter.onItemClick = {
            val intent = Intent(this, DetailPromoActivity::class.java)
            intent.putExtra("promo", it)
            startActivity(intent)
        }
    }

    private fun initUser(data: UserEntity?) {
        if (data == null) {
            val user = UserEntity(
                userId = "1",
                accountNumber = "133-155-177",
                balance = "400000",
                bankName = "Bank BNI",
                userName = "Nama Nasabah"
            )
            viewModel.insertUser(user)
        }
    }

    private fun showData(data: List<UserEntity>?) {
        with(binding) {
            if (!data.isNullOrEmpty()) {
                val bal = data.map { it.balance }.joinToString(", ")
                balance.text = "Rp.$bal"
                name.text = data.map { it.userName }[0] ?: ""
            }
        }
    }

    private fun onView() {
        with(binding) {
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(2).isEnabled = false
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home -> {}
                    R.id.history -> {
                        val intent = Intent(this@HomeActivity, TransHistoryActivity::class.java)
                        startActivity(intent)
                    }

                    R.id.notification -> {}
                    R.id.profile -> {}
                }
                true
            }
        }
    }

    private fun onClick() {
        with(binding) {
            fab.setOnClickListener {
                val intent = Intent(this@HomeActivity, ScannerActivity::class.java)
                startActivity(intent)
            }

            history.setOnClickListener {
                val intent = Intent(this@HomeActivity, TransHistoryActivity::class.java)
                startActivity(intent)
            }
        }

    }
}