package com.nadji.myapp.data.repository

import com.nadji.myapp.common.base.BaseRepository
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.datasource.NetworkDatasource
import com.nadji.myapp.data.network.model.PromoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NetworkRepository {
    suspend fun promo(): Flow<Resource<PromoResponse>>
}

class NetworkRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
) : NetworkRepository, BaseRepository() {
    override suspend fun promo(): Flow<Resource<PromoResponse>> = flow {
        emit(proceed { networkDatasource.promo() })
    }
}