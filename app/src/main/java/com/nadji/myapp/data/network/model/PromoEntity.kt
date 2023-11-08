package com.nadji.myapp.data.network.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "promo")
@Parcelize
data class PromoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("name_promo")
    val namePromo: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("alt")
    val alt: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("desc_promo")
    val descPromo: String? = null,

    @field:SerializedName("desc")
    val desc: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable
