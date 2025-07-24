package com.mehmetfatihkose.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Todo veri modeli - Room veritabanı entity'si
 * Bu sınıf, yapılacak görevlerin veri yapısını tanımlar
 */
@Entity(tableName = "todos") // Veritabanında "todos" tablosu olarak saklanacak
data class Todo(
    @PrimaryKey(autoGenerate = true) // Otomatik artan birincil anahtar
    val id: Int = 0,
    val title: String, // Görevin başlığı
    val isCompleted: Boolean = false, // Görevin tamamlanma durumu (varsayılan: false)
    val createdAt: Long = System.currentTimeMillis() // Görevin oluşturulma zamanı
) 