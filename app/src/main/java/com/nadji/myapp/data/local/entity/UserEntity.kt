package com.nadji.myapp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey
    @field:SerializedName("user_id")
    val userId: String = "",

    @field:SerializedName("account_number")
    val accountNumber: String? = null,

    @field:SerializedName("balance")
    val balance: String? = null,

    @field:SerializedName("bank_name")
    val bankName: String? = null,

    @field:SerializedName("user_name")
    val userName: String? = null
) : Parcelable