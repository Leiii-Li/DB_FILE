package com.lee.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Scene(
    @PrimaryKey
    var id: Long,
    @ColumnInfo
    var value: String?
)