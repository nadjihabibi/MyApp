package com.nadji.myapp.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nadji.myapp.databinding.ActivityPaymentSummaryBinding
import com.nadji.myapp.presentation.main.home.HomeActivity

class PaymentSummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentSummaryBinding

    private var idPayment: String = ""
    private var bankSource: String = ""
    private var merchName: String = ""
    private var ammount: String = ""
    private var userProfileName: String = ""
    private var userProfileId: String = ""
    private var userProfileBank: String = ""
    private var userProfileBalance: String = ""
    private var dateTrans: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    private fun initView() {
        with(binding) {
            idTrans.text = idPayment
            merchant.text = merchName
            totalBayar.text = ammount
            bank.text = userProfileBank
            balance.text = userProfileBalance
            date.text = dateTrans
            layoutConfirm.setOnClickListener {
                val intent = Intent(this@PaymentSummaryActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initData() {
        idPayment = intent.getStringExtra("id").toString()
        userProfileId = intent.getStringExtra("userProfileId").toString()
        bankSource = intent.getStringExtra("bankSource").toString()
        merchName = intent.getStringExtra("merchName").toString()
        ammount = intent.getStringExtra("ammount").toString()
        userProfileName = intent.getStringExtra("userProfileName").toString()
        userProfileBank = intent.getStringExtra("userProfileBank").toString()
        userProfileBalance = intent.getStringExtra("userProfileBalance").toString()
        dateTrans = intent.getStringExtra("date").toString()
    }

    override fun onBackPressed() {
        val intent = Intent(this@PaymentSummaryActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}