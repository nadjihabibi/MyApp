package com.nadji.myapp.data.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PortofolioResponse(

    @field:SerializedName("PortofolioResponse")
    val portofolioResponse: List<PortofolioResponseItem?>? = null
)

data class PortofolioResponseItem(

    @field:SerializedName("data")
    val data: List<DataItems?>? = null,

    @field:SerializedName("type")
    val type: String? = null
)

data class DataItems(

    @field:SerializedName("data")
    val data: List<DataItems?>? = null,

    @field:SerializedName("percentage")
    val percentage: String? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("nominal")
    val nominal: Int? = null,

    @field:SerializedName("trx_date")
    val trxDate: String? = null
) : Serializable

data class Data(

    @field:SerializedName("month")
    val month: List<Int?>? = null
)
