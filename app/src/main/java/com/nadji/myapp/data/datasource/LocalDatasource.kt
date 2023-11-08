package com.nadji.myapp.data.datasource

import com.nadji.myapp.data.local.dao.LocalService
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.PromoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDatasource {
    suspend fun insertPromo(promoEntity: PromoEntity)
    suspend fun getPromo(): Flow<List<PromoEntity>>
    suspend fun insertUser(userEntity: UserEntity)
    suspend fun updateUser(userId: String, field: String)
    suspend fun getUser(): Flow<List<UserEntity>>
    suspend fun getUserById(userId: String): Flow<UserEntity>
    suspend fun insertHistory(historyEntity: HistoryEntity)
    suspend fun getHistory(): Flow<List<HistoryEntity>>

//    suspend fun insertStore(storeEntity: StoreEntity)
//    suspend fun getStore(): Flow<List<StoreEntity>>
//    suspend fun getStoreDetail(id: String): Flow<StoreEntity>
    suspend fun deletePromo()
}

class LocalDatasourceImpl @Inject constructor(
    private val localService: LocalService
) : LocalDatasource {
    override suspend fun insertPromo(promoEntity: PromoEntity) {
        return localService.insertPromo(promoEntity)
    }

    override suspend fun getPromo(): Flow<List<PromoEntity>> {
        return localService.getPromo()
    }

    override suspend fun insertUser(userEntity: UserEntity) {
        return localService.insertUser(userEntity)
    }

    override suspend fun updateUser(userEntity: String, field: String) {
        return localService.updateUser(userEntity, field)
    }

    override suspend fun getUser(): Flow<List<UserEntity>> {
        return localService.getUser()
    }

    override suspend fun getUserById(userId: String): Flow<UserEntity> {
        return localService.getUserById(userId)
    }

    override suspend fun insertHistory(historyEntity: HistoryEntity) {
        return localService.insertHistory(historyEntity)
    }

    override suspend fun getHistory(): Flow<List<HistoryEntity>> {
        return localService.getHistory()
    }

    override suspend fun deletePromo() {
        return localService.deletePromo()
    }
}