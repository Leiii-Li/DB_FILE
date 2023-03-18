package com.lee.myapplication.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Scene::class],
    version = 1,
    exportSchema = false
)
abstract class SceneDatabase : RoomDatabase() {
    abstract fun sceneDao(): SceneDao

    companion object {
        private const val DATABASE_NAME = "mlauncher-db"

        // For Singleton instantiation
        @Volatile
        private var instance: SceneDatabase? = null

        fun getInstance(context: Context): SceneDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SceneDatabase {
            return Room.databaseBuilder(context, SceneDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
