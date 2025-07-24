package com.mehmetfatihkose.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mehmetfatihkose.todolist.ui.theme.TodoListTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mehmetfatihkose.todolist.R // String kaynaklarına erişim için R sınıfı import edildi
import com.mehmetfatihkose.todolist.data.Todo
import com.mehmetfatihkose.todolist.ui.TodoViewModel
import com.mehmetfatihkose.todolist.ui.components.TodoItem
import com.mehmetfatihkose.todolist.ui.theme.TodoListTheme

/**
 * Ana aktivite sınıfı
 * MVVM mimarisinin View katmanı
 * Kullanıcı etkileşimlerini yönetir ve ViewModel ile iletişim kurar
 * Tamamen Jetpack Compose kullanılarak oluşturulmuştur
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val todoViewModel: TodoViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )
            
            // Collect theme state from ViewModel
            val isDarkTheme by todoViewModel.isDarkTheme.collectAsStateWithLifecycle()
            
            TodoListTheme(darkTheme = isDarkTheme) {
                TodoApp(todoViewModel)
            }
        }
    }

}

/**
 * Ana uygulama UI bileşeni
 * Tüm uygulama arayüzünü oluşturur
 * @param viewModel TodoViewModel - UI verilerini ve kullanıcı etkileşimlerini yönetir
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(viewModel: TodoViewModel) {
    // ViewModel'den verileri topla
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
    
    // UI durumları için state tanımlamaları
    var todoText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    // Ana uygulama yapısı
    Scaffold(
        topBar = {
            // TopAppBar - XML ActionBar/Toolbar yerine Compose kullanılarak oluşturuldu
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_title)) }, // Başlık için string kaynağı kullanıldı
                actions = {
                    // Tema değiştirme butonu
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode, // Tema durumuna göre ikon değişimi
                            contentDescription = stringResource(R.string.toggle_theme) // Erişilebilirlik için string kaynağı kullanıldı
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Todo ekleme alanı
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Todo giriş alanı (ekleme veya düzenleme için) - XML EditText yerine Compose OutlinedTextField kullanıldı
                OutlinedTextField(
                    value = todoText,
                    onValueChange = { 
                        todoText = it 
                        isError = false // Kullanıcı yazmaya başladığında hata durumunu sıfırla
                    },
                    label = { Text(if (isEditing) "Todo düzenle" else stringResource(R.string.add_todo_hint)) }, // Düzenleme moduna göre etiket değişimi, string kaynağı kullanıldı
                    isError = isError, // Hata durumunda görsel geri bildirim - Material Design 3 hata stili
                    supportingText = if (isError) {
                        { Text("Todo başlığı boş olamaz") } // Hata mesajı - XML error attribute yerine Compose supportingText
                    } else null,
                    modifier = Modifier.weight(1f) // Mevcut alanın çoğunu kullan - XML layout_weight özelliğinin Compose karşılığı
                )
                
                // Buton satırı
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Düzenleme modunda iptal butonu göster
                    if (isEditing) {
                        Button(
                            onClick = {
                                // Düzenleme modunu kapat
                                isEditing = false
                                editingTodo = null
                                todoText = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("İptal")
                        }
                    }
                    
                    // Todo ekleme veya güncelleme butonu - XML Button yerine Compose Button kullanıldı
                    Button(
                        onClick = {
                            if (todoText.isNotBlank()) {
                                if (isEditing && editingTodo != null) {
                                    // Mevcut todo'yu güncelle
                                    val updatedTodo = editingTodo!!.copy(title = todoText.trim())
                                    viewModel.update(updatedTodo)
                                    // Düzenleme modunu kapat
                                    isEditing = false
                                    editingTodo = null
                                } else {
                                    // Yeni todo ekle
                                    viewModel.insert(Todo(title = todoText.trim()))
                                }
                                // Input alanını temizle
                                todoText = ""
                            } else {
                                // Boş todo girişinde hata durumunu aktifleştir
                                isError = true
                            }
                        },
                        // Material Design 3 buton stili otomatik olarak uygulanır
                    ) {
                        Text(if (isEditing) "Güncelle" else stringResource(R.string.add)) // Düzenleme moduna göre buton metni değişimi, string kaynağı kullanıldı
                    }
                }
            }
            
            // Todo listesi
            if (todos.isEmpty()) {
                // Boş liste durumu - kullanıcıya bilgi mesajı göster
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.empty_state_message), // Boş liste durumu için string kaynağı kullanıldı
                        style = MaterialTheme.typography.bodyLarge, // Material Design 3 tipografi stilini kullan - XML textAppearance özelliğinin Compose karşılığı
                        textAlign = TextAlign.Center // Metni ortala - XML gravity="center" özelliğinin Compose karşılığı
                    ) // XML TextView yerine Compose Text kullanıldı
                }
            } else {
                // Todo listesini LazyColumn ile göster (RecyclerView'ın Compose karşılığı)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Öğeler arasında 8dp boşluk bırak - XML itemDecoration yerine Compose verticalArrangement
                ) {
                    val todoList = todos
                    // Her bir todo için ayrı bir öğe oluştur - XML RecyclerView.Adapter yerine Compose for döngüsü
                    for (todo in todoList) {
                        // key parametresi ile verimli güncellemeler sağla - RecyclerView'daki DiffUtil mantığının Compose karşılığı
                        item(key = todo.id) {
                            TodoItem(
                                todo = todo,
                                onCheckedChange = { viewModel.toggleTodoCompletion(todo) }, // Tamamlanma durumunu değiştir
                                onDeleteClick = { viewModel.delete(todo) }, // Todo'yu sil
                                onTitleClick = {
                                    // Düzenleme modunu aktifleştir
                                    isEditing = true
                                    editingTodo = todo
                                    todoText = todo.title // Mevcut başlığı input alanına yerleştir
                                }
                            ) // TodoItem Composable kullanılarak XML yerine Compose ile UI oluşturuldu
                        }
                    }
                } // LazyColumn, RecyclerView'ın Compose karşılığıdır - XML RecyclerView yerine Compose LazyColumn
            }
        }
    }
}