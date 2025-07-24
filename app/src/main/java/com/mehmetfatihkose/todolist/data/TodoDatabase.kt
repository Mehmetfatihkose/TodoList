package com.mehmetfatihkose.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room veritabanı sınıfı
 * Singleton pattern kullanarak tek bir veritabanı instance'ı sağlar
 */
@Database(
    entities = [Todo::class], // Veritabanında kullanılacak entity'ler
    version = 1, // Veritabanı versiyonu
    exportSchema = false // Schema export'u kapalı
)
abstract class TodoDatabase : RoomDatabase() {
    
    /**
     * TodoDao'ya erişim sağlar
     */
    abstract fun todoDao(): TodoDao
    
    companion object {
        // Volatile keyword - thread-safe singleton için gerekli
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        
        /**
         * Veritabanı instance'ını döndürür
         * Singleton pattern - uygulama genelinde tek instance
         */
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                // Eğer instance yoksa, yeni bir tane oluştur
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Application context kullan
                    TodoDatabase::class.java, // Veritabanı sınıfı
                    "todo_database" // Veritabanı adı
                ).build()
                INSTANCE = instance // Instance'ı kaydet
                instance // Instance'ı döndür
            }
        }
    }
} 