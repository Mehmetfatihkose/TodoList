package com.mehmetfatihkose.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) - Veritabanı işlemlerini yönetir
 * Room veritabanı ile etkileşim için gerekli metodları tanımlar
 */
@Dao // Room DAO olduğunu belirtir
interface TodoDao {
    
    /**
     * Tüm todo'ları getir - En son oluşturulanlar önce gelir
     * LiveData kullanarak reaktif veri akışı sağlar
     */
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getAllTodos(): LiveData<List<Todo>>
    
    /**
     * Tüm todo'ları Flow olarak getir - Compose ile kullanım için daha uygun
     * Kotlin Flow kullanarak reaktif veri akışı sağlar
     */
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getAllTodosFlow(): Flow<List<Todo>>
    
    /**
     * Yeni todo ekle
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    @Insert
    suspend fun insertTodo(todo: Todo)
    
    /**
     * Mevcut todo'yu güncelle
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    @Update
    suspend fun updateTodo(todo: Todo)
    
    /**
     * Todo'yu sil
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    @Delete
    suspend fun deleteTodo(todo: Todo)
    
    /**
     * Tamamlanmış tüm todo'ları sil
     * Özel SQL sorgusu kullanır
     */
    @Query("DELETE FROM todos WHERE isCompleted = 1")
    suspend fun deleteCompletedTodos()
}