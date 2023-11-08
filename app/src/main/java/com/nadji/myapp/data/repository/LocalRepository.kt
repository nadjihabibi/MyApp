package com.nadji.myapp.data.repository

import com.nadji.myapp.common.base.BaseRepository
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.datasource.LocalDatasource
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.StoreEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.PromoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalRepository {
    suspend fun insertPromo(promoEntity: PromoEntity): Flow<Resource<Unit>>
    suspend fun getPromo(): Flow<Resource<List<PromoEntity>>>
    suspend fun insertUser(userEntity: UserEntity): Flow<Resource<Unit>>
    suspend fun updateUser(id: String, field: String): Flow<Resource<Unit>>
    suspend fun getUser(): Flow<Resource<List<UserEntity>>>
    suspend fun getUserById(id: String): Flow<Resource<UserEntity>>
    suspend fun insertHistory(historyEntity: HistoryEntity): Flow<Resource<Unit>>
    suspend fun getHistory(): Flow<Resource<List<HistoryEntity>>>
    suspend fun deletePromo(): Flow<Resource<Unit>>
}

class LocalRepositoryImpl @Inject constructor(
    private val localDatasource: LocalDatasource
) : LocalRepository, BaseRepository() {

    override suspend fun insertPromo(promoEntity: PromoEntity): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.insertPromo(promoEntity) })
    }

    override suspend fun getPromo(): Flow<Resource<List<PromoEntity>>> = flow {
        emit(proceed { localDatasource.getPromo().first() })
    }

    override suspend fun insertUser(userEntity: UserEntity): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.insertUser(userEntity) })
    }

    override suspend fun updateUser(id: String, field: String): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.updateUser(id, field) })
    }


    override suspend fun getUser(): Flow<Resource<List<UserEntity>>> = flow {
        emit(proceed { localDatasource.getUser().first() })
    }

    override suspend fun getUserById(id: String): Flow<Resource<UserEntity>> = flow {
        emit(proceed { localDatasource.getUserById(id).first() })
    }

    override suspend fun insertHistory(historyEntity: HistoryEntity): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.insertHistory(historyEntity) })
    }

    override suspend fun getHistory(): Flow<Resource<List<HistoryEntity>>> = flow {
        emit(proceed { localDatasource.getHistory().first() })
    }


//    override suspend fun insertStore(storeEntity: StoreEntity): Flow<Resource<Unit>> = flow {
//        emit(proceed { localDatasource.insertStore(storeEntity) })
//    }
//
//    override suspend fun getStore(): Flow<Resource<List<StoreEntity>>> = flow {
//        emit(proceed { localDatasource.getStore().first() })
//    }
//
//    override suspend fun getStoreDetail(id: String): Flow<Resource<StoreEntity>> = flow {
//        emit(proceed { localDatasource.getStoreDetail(id).first() })
//    }
//
    override suspend fun deletePromo(): Flow<Resource<Unit>> = flow {
        emit(proceed { localDatasource.deletePromo() })
    }
}