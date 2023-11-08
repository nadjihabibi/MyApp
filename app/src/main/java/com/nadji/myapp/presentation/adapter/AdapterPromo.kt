package com.nadji.myapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.nadji.myapp.R
import com.nadji.myapp.data.network.model.PromoEntity

class AdapterPromo : RecyclerView.Adapter<AdapterPromo.PromoViewHolder>() {
    private val listPromo = ArrayList<PromoEntity>()

    fun setList(filterList: List<PromoEntity>?) {
        if (filterList == null) return
        this.listPromo.clear()
        this.listPromo.addAll(filterList)
    }

    var onItemClick: ((PromoEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_promo_list_row, parent, false
        )

        val screenWidth = parent.context.resources.displayMetrics.widthPixels
        val itemWidth = (screenWidth * 0.8).toInt()
        val layoutParams = ViewGroup.LayoutParams(itemWidth, 300)
        view.layoutParams = layoutParams
        return PromoViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val promo = listPromo[position]
        holder.bind(promo)
        holder.card.setOnClickListener {
            notifyDataSetChanged()
            onItemClick?.invoke(promo)
        }
    }

    override fun getItemCount(): Int = listPromo.size

    inner class PromoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var card: ConstraintLayout = v.findViewById(R.id.card_row)
        var nama: TextView = v.findViewById(R.id.row_name)
        var lokasi: TextView = v.findViewById(R.id.lokasi)
        fun bind(promo: PromoEntity) {
            nama.text = promo.nama
            lokasi.text = promo.lokasi
        }
    }
}