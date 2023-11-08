package com.nadji.myapp.data.datasource

import com.nadji.myapp.data.network.model.PromoResponse
import com.nadji.myapp.data.network.service.NetworkService
import okhttp3.MultipartBody
import javax.inject.Inject

interface NetworkDatasource {
    suspend fun promo(): PromoResponse
}

class NetworkDatasourceImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkDatasource {
    override suspend fun promo(): PromoResponse {
        return networkService.promo()
    }
}