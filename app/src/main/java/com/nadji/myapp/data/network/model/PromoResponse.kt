package com.nadji.myapp.data.network.model

import com.google.gson.annotations.SerializedName

data class PromoResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class Meta(
	@field:SerializedName("pagination")
	val pagination: Pagination? = null
)

data class Pagination(

	@field:SerializedName("pageCount")
	val pageCount: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

data class DataItem(

	@field:SerializedName("attributes")
	val attributes: PromoEntity? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

