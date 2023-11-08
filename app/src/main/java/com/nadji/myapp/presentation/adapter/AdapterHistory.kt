package com.nadji.myapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.databinding.ItemHistoryBinding

class AdapterHistory(
    val data: List<HistoryEntity?>?,
    val itemClick: OnClickListener
) : RecyclerView.Adapter<AdapterHistory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        if (position == data!!.size - 1) {
            holder.binding.div.visibility = View.GONE
        } else {
            holder.binding.div.visibility = View.VISIBLE
        }
        holder.bind(item!!)
    }

    override fun getItemCount(): Int = data!!.size

    inner class ViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryEntity) {
            with(binding) {
                name.text = item.merchantName
                subName.text = item.date
                total.text = "-Rp${item.amount}"

                root.setOnClickListener {
                    itemClick.detail(item)
                }
            }

        }
    }

    interface OnClickListener {
        fun detail(item: HistoryEntity)
    }
}