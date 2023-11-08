package com.nadji.myapp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nadji.myapp.data.network.model.PromoEntity
import com.nadji.myapp.databinding.ActivityDetailPromoBinding

class DetailPromoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPromoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPromoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedEntity = intent.getParcelableExtra<PromoEntity>("promo")

        if (receivedEntity != null) {
            with(binding) {
                name.text = receivedEntity.nama
                lokasi.text = receivedEntity.lokasi
                desc.text = receivedEntity.desc

                ivBack.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }
}