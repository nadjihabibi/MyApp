package com.nadji.myapp.presentation.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nadji.myapp.R
import com.nadji.myapp.common.utils.Constant
import com.nadji.myapp.common.utils.showAlert
import com.nadji.myapp.common.utils.showToast
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.databinding.ActivityConfirmBinding
import com.nadji.myapp.presentation.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class ConfirmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmBinding
    private var isDialogShowing = false
    private var dialog: Dialog? = null
    private val viewModel: HomeViewModel by viewModels()

    private var bankSource: String = ""
    private var idPay: String = ""
    private var merchName: String = ""
    private var ammount: String = ""

    private var userProfileId: String = ""
    private var userProfileName: String = ""
    private var userProfilebank: String = ""
    private var userProfileBalance: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputString = intent.getStringExtra("payment")
        val parts = inputString?.split(".")
        if (parts != null) {
            bankSource = parts[0]
            idPay = parts[1]
            merchName = parts[2]
            ammount = parts[3]
        }

        binding.layoutConfirm.setOnClickListener {
            if (isDialogShowing) {
                hideBottomDialog()
            } else {
                showBottomDialog()
            }
        }
        initView()
    }

    private fun initView() {
        with(binding) {
            merchantName.text = merchName
            idPayment.text = idPay
            nominal.text = "Rp.$ammount"
        }

        lifecycleScope.apply {
            launchWhenStarted {
                viewModel.getUserResult.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            initValue(it.data)
                            Log.d("observeData", "observeData:xxx ${it.data}")
                        }

                        is Resource.Error -> {
                            showAlert(Constant.APP_NAME, it.message.toString())
                            Log.d("observeData", "observeData: ${it.exception} or ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun initValue(data: List<UserEntity>?) {
        if (data != null) {
            userProfileName = data.map { it.userName }[0] ?: ""
            userProfilebank = data.map { it.bankName }[0] ?: ""
            userProfileBalance = data.map { it.balance }[0] ?: ""
            userProfileId = data.map { it.userId }[0] ?: ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_layout)

        val btnPayment = dialog.findViewById<TextView>(R.id.btn_payment)
        val bank = dialog.findViewById<TextView>(R.id.nama_user)
        val saldo = dialog.findViewById<TextView>(R.id.saldo)

        bank.text = userProfilebank
        saldo.text = "Rp.$userProfileBalance"

        btnPayment.setOnClickListener {
            val ball = userProfileBalance.toInt()
            val amm =  ammount.toInt()
            if (ball < amm) {
                showToast("Saldo anda tidak cukup")
            } else {
                val count = ball - amm
                viewModel.updateUser(userProfileId, count.toString())

                val time = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val current = formatter.format(time)
                val history = HistoryEntity(
                    transId = idPay,
                    merchantName = merchName,
                    amount = ammount,
                    date = current
                )
                viewModel.insertHistory(history)

                val intent = Intent(this@ConfirmActivity, PaymentSummaryActivity::class.java)
                intent.putExtra("id", idPay)
                intent.putExtra("userProfileId", userProfileId)
                intent.putExtra("bankSource", bankSource)
                intent.putExtra("merchName", merchName)
                intent.putExtra("ammount", ammount)
                intent.putExtra("userProfileName", userProfileName)
                intent.putExtra("userProfileBank", userProfilebank)
                intent.putExtra("userProfileBalance", count.toString())
                intent.putExtra("date", current)
                startActivity(intent)
            }

        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun hideBottomDialog() {
        dialog?.dismiss()
        isDialogShowing = false
    }
}