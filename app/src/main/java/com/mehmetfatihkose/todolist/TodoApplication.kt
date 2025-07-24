package com.mehmetfatihkose.todolist

import android.app.Application
import com.mehmetfatihkose.todolist.data.TodoDatabase

class TodoApplication : Application() {
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}
