# TodoList - Modern Android Uygulaması

## 📱 Proje Hakkında

Bu proje, **%100 Jetpack Compose** kullanılarak geliştirilmiş modern bir Android Todo List uygulamasıdır. XML layout dosyaları tamamen kaldırılmış ve tüm UI bileşenleri Compose ile yazılmıştır.

> **Önemli:** Bu proje XML'den Compose'a tam dönüşüm örneğidir. Geleneksel Android UI geliştirmeden modern Compose yaklaşımına geçiş sürecini gösterir.

## 🚀 Özellikler

### Todo Yönetimi
- ➕ **Todo Ekleme** - Yeni görev oluşturma
- ✏️ **Todo Düzenleme** - Mevcut görevi güncelleme
- ✅ **Todo Tamamlama** - Görev durumu değiştirme
- 🗑️ **Todo Silme** - Görev kaldırma
- 💾 **Kalıcı Veri** - Room veritabanı ile saklama

### UI/UX Özellikleri
- 🌙 **Tema Değiştirme** - Açık/Koyu tema geçişi
- 📱 **Responsive Design** - Farklı ekran boyutları
- ⚡ **Smooth Animations** - Akıcı geçişler
- 🎨 **Material You** - Android 12+ dinamik renkler
- 📝 **Boş Durum Yönetimi** - Kullanıcı dostu mesajlar

### Teknik Özellikler
- ✅ **%100 Jetpack Compose** - XML layout yok
- ✅ **Material Design 3** - Modern tasarım dili
- ✅ **MVVM Architecture** - Temiz kod mimarisi
- ✅ **Kotlin Coroutines** - Asenkron işlemler
- ✅ **StateFlow & Flow** - Reaktif programlama

## Ekran Görüntüsü

> Uygulamanın hem light hem de dark modda ekran görüntülerini buraya ekleyebilirsiniz.

## Kurulum

1. **Projeyi klonlayın:**
   ```sh
   git clone <repo-url>
   ```
2. **Android Studio ile açın.**
3. **Gerekli bağımlılıkların yüklenmesini bekleyin.**
4. **Bir emülatör veya gerçek cihaz seçin.**
5. **Projeyi çalıştırın (Run > Run 'app').**

## Kullanım

- Yeni görev eklemek için üstteki metin kutusuna yazıp "Ekle" butonuna basın.
- Görevleri tamamlandı olarak işaretlemek için kutucuğa tıklayın.
- Görevleri silmek için çöp kutusu ikonuna tıklayın.
- Sağ üstteki güneş/ay ikonuna tıklayarak tema (dark/light) değiştirebilirsiniz.

## Proje Yapısı

### Paket Yapısı

```
com.mehmetfatihkose.todolist/
├── MainActivity.kt                # Ana aktivite ve UI bileşenleri (Jetpack Compose)
├── data/                          # Veri katmanı
│   ├── Todo.kt                    # Veri modeli (Entity)
│   ├── TodoDao.kt                 # Veritabanı erişim nesnesi (DAO)
│   ├── TodoDatabase.kt            # Room veritabanı yapılandırması
│   └── TodoRepository.kt          # Repository (veri kaynağı yönetimi)
└── ui/                            # UI bileşenleri
    ├── TodoViewModel.kt           # ViewModel (UI ve veri arasında köprü)
    ├── components/                # Compose UI bileşenleri
    │   └── TodoItem.kt            # Tek bir todo öğesi için Compose bileşeni
    └── theme/                     # UI tema bileşenleri
        └── TodoTheme.kt           # Material Design 3 tema yapılandırması
```

> **Not:** XML tabanlı layout dosyaları (`activity_main.xml`, `item_todo.xml`) ve `TodoAdapter.kt` dosyası Jetpack Compose'a geçiş sürecinde kaldırılmıştır.

## XML'den Jetpack Compose'a Geçiş

Bu projede, geleneksel XML tabanlı layout sisteminden modern Jetpack Compose UI toolkit'ine geçiş yapılmıştır. Bu geçişin ana adımları:

1. **XML Layout Dosyalarının Kaldırılması:**
   - `activity_main.xml` ve `item_todo.xml` dosyaları kaldırıldı
   - `res/layout` klasörü tamamen kaldırıldı
   - XML layout inflation yerine Compose'un deklaratif UI yaklaşımı benimsendi

2. **RecyclerView Adapter'ın Kaldırılması:**
   - `TodoAdapter.kt` dosyası kaldırıldı
   - RecyclerView yerine Compose'un `LazyColumn` bileşeni kullanıldı
   - ViewHolder pattern yerine Compose'un recomposition mekanizması kullanıldı
   - DiffUtil yerine Compose'un key parametresi ile verimli güncellemeler sağlandı

3. **Compose UI Bileşenlerinin Geliştirilmesi:**
   - `TodoItem.kt` - Tek bir todo öğesini gösteren Compose bileşeni
     - XML CardView → Compose Card
     - XML TextView → Compose Text
     - XML CheckBox → Compose Checkbox
     - XML ImageButton → Compose IconButton
   - `MainActivity.kt` içinde `TodoApp` Composable fonksiyonu - Ana uygulama UI'ı
     - XML EditText → Compose OutlinedTextField
     - XML Button → Compose Button
     - XML RecyclerView → Compose LazyColumn

4. **String Kaynaklarının Kullanımı:**
   - Tüm sabit metinler `strings.xml` dosyasından alınarak çoklu dil desteği sağlandı
   - `stringResource()` fonksiyonu ile string kaynaklarına erişim
   - Erişilebilirlik için contentDescription'lar string kaynaklarından alındı

5. **Material Design 3 Entegrasyonu:**
   - Modern ve uyarlanabilir tasarım sistemi
   - Dinamik renk desteği (Android 12+)
   - Light/Dark tema desteği
   - Material You tasarım dili ile uyumlu bileşenler
   - Material tipografi ve renk şeması kullanımı

6. **AndroidManifest.xml Güncellemesi:**
   - XML tabanlı tema referansları kaldırıldı
   - Compose uyumlu tema ayarları eklendi

7. **Yeni Özellikler Eklenmesi:**
   - Todo düzenleme özelliği
   - Boş liste durumu yönetimi
   - Hata durumu yönetimi ve görsel geri bildirim

Bu geçiş sayesinde daha az kod ile daha modern, bakımı kolay ve ölçeklenebilir bir UI elde edilmiştir. XML tabanlı UI geliştirmeye kıyasla Compose ile:

- Daha az boilerplate kod
- Daha iyi tip güvenliği
- Daha kolay state yönetimi
- Daha hızlı geliştirme süreci
- Daha modern ve tutarlı UI

## Kullanılan Teknolojiler

- **Kotlin** - Modern, güvenli ve özlü programlama dili
- **Android Jetpack**
  - **Compose** - Modern UI toolkit
  - **Room** - SQLite veritabanı soyutlama katmanı
  - **ViewModel** - UI verilerini yaşam döngüsü bilinçli şekilde yönetme
  - **Flow/StateFlow** - Reaktif veri akışı
  - **Coroutines** - Asenkron programlama
- **Material Design 3** - Modern ve uyarlanabilir tasarım sistemi
- **MVVM Mimarisi** - Model-View-ViewModel mimari deseni

## Katkı
Katkıda bulunmak isterseniz lütfen bir pull request gönderin veya issue açın.

## Lisans
Bu proje MIT lisansı ile lisanslanmıştır.