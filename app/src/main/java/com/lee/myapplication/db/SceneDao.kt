package com.lee.myapplication.db

import androidx.room.*

@Dao
interface SceneDao {
    @Query("SELECT * from scene")
    fun queryAll(): List<Scene>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scene: Scene)

    @Delete
    fun delete(scene: Scene)

    @Query("SELECT * from scene where id = :id")
    fun query(id: Long): Scene
}