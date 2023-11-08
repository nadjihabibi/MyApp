package com.nadji.myapp.presentation.main.home

import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.PromoEntity
import com.nadji.myapp.data.network.model.PromoResponse
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody

interface HomeContract {
    val insertUserResult: StateFlow<Resource<Unit>>
    fun insertUser(user: UserEntity)
    fun updateUser(user: String, field: String)

    val getUserResult: StateFlow<Resource<List<UserEntity>>>
    fun getUser()

    val getUserByIdResult: StateFlow<Resource<UserEntity>>
    fun getUserById(id: String)

    val insertHistoryResult: StateFlow<Resource<Unit>>
    fun insertHistory(history: HistoryEntity)

    val getHistoryResult: StateFlow<Resource<List<HistoryEntity>>>
    fun getHistory()

    val promoResult: StateFlow<Resource<PromoResponse>>
    fun promo()

    val getPromoResult: StateFlow<Resource<List<PromoEntity>>>
    fun getPromo()

    val insertPromoResult: StateFlow<Resource<Unit>>
    fun insertPromo(promoEntity: PromoEntity)

    val deleteResult: StateFlow<Resource<Unit>>
    fun deletePromo()

}