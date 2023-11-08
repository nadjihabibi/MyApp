package com.nadji.myapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadji.myapp.data.local.dao.LocalService
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.PromoEntity

@Database(
    entities = [UserEntity::class, HistoryEntity::class, PromoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localService(): LocalService
}