package com.nadji.myapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nadji.myapp.R
import com.nadji.myapp.data.network.model.DataItems
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PortofolioAdapter(private val dataItemsList: List<DataItems>) :
    RecyclerView.Adapter<PortofolioAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.labelTextView)
        val percentageTextView: TextView = itemView.findViewById(R.id.percentageTextView)
        val nominalTextView: TextView = itemView.findViewById(R.id.nominalTextView)
        val trxDateTextView: TextView = itemView.findViewById(R.id.trxDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_portofolio, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = dataItemsList[position]
        val formattedDate = formatDate(dataItem.trxDate.toString())
        holder.labelTextView.text = dataItem.label
        holder.percentageTextView.text = "${dataItem.percentage} %"
        holder.nominalTextView.text = "Rp${dataItem.nominal}"
        holder.trxDateTextView.text = formattedDate
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("ddMMyyyy", Locale.US)
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)

        return try {
            val date: Date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Format Tidak Valid"
        }
    }

    override fun getItemCount(): Int {
        return dataItemsList.size
    }
}