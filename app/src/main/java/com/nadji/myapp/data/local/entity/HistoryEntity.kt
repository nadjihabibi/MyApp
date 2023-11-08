package com.nadji.myapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history")
@Parcelize
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Long = 0,

    @field:SerializedName("trans_id")
    val transId: String? = null,

    @field:SerializedName("merchant_name")
    val merchantName: String? = null,

    @field:SerializedName("amount")
    val amount: String? = null,

    @field:SerializedName("date")
    val date: String? = null,

    ) : Parcelable