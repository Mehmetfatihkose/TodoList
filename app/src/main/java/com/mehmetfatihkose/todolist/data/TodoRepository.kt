package com.mehmetfatihkose.todolist.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository sınıfı - Veri katmanı
 * ViewModel ile veritabanı arasında köprü görevi görür
 * Single source of truth prensibini uygular
 */
class TodoRepository(private val todoDao: TodoDao) {
    
    /**
     * Tüm todo'ları LiveData olarak döndürür
     * Reaktif veri akışı sağlar
     */
    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodos()
    
    /**
     * Tüm todo'ları Flow olarak döndürür
     * Compose ile kullanım için daha uygun
     */
    val allTodosFlow: Flow<List<Todo>> = todoDao.getAllTodosFlow()
    
    /**
     * Yeni todo ekle
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    suspend fun insert(todo: Todo) {
        todoDao.insertTodo(todo)
    }
    
    /**
     * Mevcut todo'yu güncelle
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    suspend fun update(todo: Todo) {
        todoDao.updateTodo(todo)
    }
    
    /**
     * Todo'yu sil
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    suspend fun delete(todo: Todo) {
        todoDao.deleteTodo(todo)
    }
    
    /**
     * Tamamlanmış tüm todo'ları sil
     * suspend fonksiyon - coroutine scope'da çalışır
     */
    suspend fun deleteCompletedTodos() {
        todoDao.deleteCompletedTodos()
    }
}