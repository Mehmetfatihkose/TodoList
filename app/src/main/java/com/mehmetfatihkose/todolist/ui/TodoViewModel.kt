package com.mehmetfatihkose.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetfatihkose.todolist.data.Todo
import com.mehmetfatihkose.todolist.data.TodoDatabase
import com.mehmetfatihkose.todolist.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel sınıfı - UI ile veri katmanı arasında köprü görevi görür
 * UI'dan bağımsız olarak verileri tutar ve yönetir
 * Configuration change'lerde (ekran döndürme vb.) verilerin korunmasını sağlar
 * Jetpack Compose ile uyumlu çalışmak için Flow ve State kullanır
 */

/**
 * ViewModel sınıfı - UI ile veri katmanı arasında köprü görevi görür
 * UI'dan bağımsız olarak verileri tutar ve yönetir
 * Configuration change'lerde (ekran döndürme vb.) verilerin korunmasını sağlar
 * Jetpack Compose ile uyumlu çalışmak için Flow ve State kullanır
 */
class TodoViewModel(application: Application) : AndroidViewModel(application) {
    
    // Repository referansı
    private val repository: TodoRepository
    
    // Tüm todo'ları tutan StateFlow
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()
    
    // Tema durumu için StateFlow
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
    
    // ViewModel başlatıldığında repository ve StateFlow'u hazırla
    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        
        // Flow'u StateFlow'a aktar
        viewModelScope.launch {
            repository.allTodosFlow.collect { todoList ->
                _todos.value = todoList
            }
        }
    }
    
    /**
     * Tema durumunu değiştir
     */
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
    
    /**
     * Yeni todo ekle
     * viewModelScope - ViewModel yaşam döngüsüne bağlı coroutine scope
     */
    fun insert(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }
    
    /**
     * Todo'yu güncelle
     */
    fun update(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(todo)
    }
    
    /**
     * Todo'yu sil
     */
    fun delete(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todo)
    }
    
    /**
     * Tamamlanmış tüm todo'ları sil
     */
    fun deleteCompletedTodos() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteCompletedTodos()
    }
    
    /**
     * Todo'nun tamamlanma durumunu değiştir
     */
    fun toggleTodoCompletion(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
        repository.update(updatedTodo)
    }
}