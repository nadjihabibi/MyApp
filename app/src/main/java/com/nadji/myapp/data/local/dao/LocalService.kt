package com.nadji.myapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.PromoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalService {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromo(promoEntity: PromoEntity)

    @Query("SELECT * FROM promo")
    fun getPromo(): Flow<List<PromoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun getUser(): Flow<List<UserEntity>>

    @Query("UPDATE user SET balance = :balance WHERE userId = :userId")
    suspend fun updateUser(userId: String, balance: String)

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserById(userId: String): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM promo")
    suspend fun deletePromo()
}