package com.mehmetfatihkose.todolist.ui.components

import androidx.compose.foundation.clickable // Tıklama işlevselliği için - XML onClick özelliğinin Compose karşılığı
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row // Layout bileşenleri için - XML LinearLayout, FrameLayout yerine Compose Row, Column, Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons // Material Design ikonları için
import androidx.compose.material.icons.filled.Delete // Silme ikonu için
import androidx.compose.material3.Card // Material Design 3 bileşenleri için
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable // Compose fonksiyonları için temel annotation
import androidx.compose.ui.Alignment // Hizalama özellikleri için - XML gravity, layout_gravity yerine
import androidx.compose.ui.Modifier // UI bileşenlerini değiştirmek için - XML attribute'ları yerine
import androidx.compose.ui.res.stringResource // String kaynaklarına erişim için
import androidx.compose.ui.text.style.TextDecoration // Metin dekorasyonu (üstü çizili stil) için
import androidx.compose.ui.text.style.TextOverflow // Uzun metinlerin kırpılması için
import androidx.compose.ui.unit.dp // Boyut birimleri için - XML dp değerleri yerine
import com.mehmetfatihkose.todolist.R // String kaynaklarına erişim için R sınıfı
import com.mehmetfatihkose.todolist.data.Todo // Todo veri modeli

/**
 * TodoItem Composable - Tek bir todo öğesini görüntüler
 * Material Design 3 Card bileşeni kullanarak modern bir görünüm sağlar
 * Todo'nun tamamlanma durumuna göre metin stilini değiştirir
 */
@Composable
fun TodoItem(
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onTitleClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Material Design 3 Card bileşeni - XML CardView yerine Compose Card kullanılarak oluşturuldu
    Card(
        modifier = modifier
            .fillMaxWidth() // Genişliği ekranın tamamına yay - XML match_parent özelliğinin Compose karşılığı
            .padding(vertical = 4.dp), // Dikey yönde 4dp boşluk bırak - XML padding özelliğinin Compose karşılığı
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Kart gölgesi/yüksekliği - XML cardElevation özelliğinin Compose karşılığı
    ) {
        // Ana satır düzeni
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Todo içeriği (checkbox ve başlık)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Mevcut alanın çoğunu kullan
            ) {
                // Tamamlanma durumu için checkbox
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = { onCheckedChange(it) },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Todo başlığı - tamamlanmışsa üstü çizili göster (XML TextView yerine Compose Text kullanıldı)
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge, // Material Design 3 tipografi stilini kullan - XML textAppearance özelliğinin Compose karşılığı
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None, // Tamamlanma durumuna göre metin stili - XML'de doğrudan karşılığı yok, programatik olarak yapılırdı
                    maxLines = 1, // Tek satırla sınırla - XML maxLines özelliğinin Compose karşılığı
                    overflow = TextOverflow.Ellipsis, // Uzun metinleri kırp - XML ellipsize özelliğinin Compose karşılığı
                    modifier = Modifier
                        .padding(end = 8.dp) // Sağ tarafta 8dp boşluk bırak - XML paddingEnd özelliğinin Compose karşılığı
                        .clickable { onTitleClick() } // Başlığa tıklandığında düzenleme için callback çağır (XML onClick yerine Compose clickable)
                )
            }
            // Silme butonu (XML ImageButton yerine Compose IconButton kullanıldı)
            IconButton(onClick = onDeleteClick) { // XML onClick özelliğinin Compose karşılığı
                Icon(
                    imageVector = Icons.Default.Delete, // Material Design 3 ikon kullan - XML drawable özelliğinin Compose karşılığı
                    contentDescription = stringResource(R.string.delete_todo), // Erişilebilirlik için string kaynağı kullanıldı - XML contentDescription özelliğinin Compose karşılığı
                    tint = MaterialTheme.colorScheme.error // Hata rengi kullan (genellikle kırmızı) - XML tint özelliğinin Compose karşılığı, Material Design 3 renk şeması
                )
            } // Compose'da IconButton, XML'deki ImageButton + ripple effect kombinasyonunun daha gelişmiş hali
        }
    }
}