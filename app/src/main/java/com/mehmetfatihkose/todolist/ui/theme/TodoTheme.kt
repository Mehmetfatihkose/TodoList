package com.mehmetfatihkose.todolist.ui.theme

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController as AndroidWindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * TodoListTheme - Uygulama genelinde kullanılan tema tanımlamaları
 * Material Design 3 renk şemalarını ve dinamik renkleri destekler
 * Android 12+ için dinamik renk desteği sunar
 */

/**
 * Light tema için renk şeması
 * Material Design 3 standartlarına uygun açık tema renkleri
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),      // Ana renk - mor
    onPrimary = Color.White,          // Ana renk üzerindeki içerik rengi
    secondary = Color(0xFF03DAC6),    // İkincil renk - turkuaz
    onSecondary = Color.Black,        // İkincil renk üzerindeki içerik rengi
    background = Color.White,         // Arka plan rengi
    onBackground = Color.Black,       // Arka plan üzerindeki içerik rengi
    surface = Color.White,            // Yüzey rengi (kartlar vb.)
    onSurface = Color.Black,          // Yüzey üzerindeki içerik rengi
    error = Color(0xFFB00020),        // Hata rengi - kırmızı
    onError = Color.White             // Hata rengi üzerindeki içerik rengi
)

/**
 * Dark tema için renk şeması
 * Material Design 3 standartlarına uygun koyu tema renkleri
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),      // Ana renk - açık mor
    onPrimary = Color.Black,          // Ana renk üzerindeki içerik rengi
    secondary = Color(0xFF03DAC6),    // İkincil renk - turkuaz
    onSecondary = Color.Black,        // İkincil renk üzerindeki içerik rengi
    background = Color(0xFF121212),   // Arka plan rengi - koyu gri
    onBackground = Color.White,       // Arka plan üzerindeki içerik rengi
    surface = Color(0xFF121212),      // Yüzey rengi (kartlar vb.)
    onSurface = Color.White,          // Yüzey üzerindeki içerik rengi
    error = Color(0xFFCF6679),        // Hata rengi - pembe/kırmızı
    onError = Color.Black             // Hata rengi üzerindeki içerik rengi
)

/**
 * TodoListTheme - Uygulamanın ana temasını belirleyen Composable fonksiyon
 * 
 * @param darkTheme Koyu tema kullanılıp kullanılmayacağı (varsayılan: sistem ayarı)
 * @param dynamicColor Dinamik renk kullanılıp kullanılmayacağı (Android 12+ için, varsayılan: true)
 * @param content İçerik Composable'ları
 */
@Composable
fun TodoListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    val context = LocalContext.current
    
    if (!view.isInEditMode) {
        DisposableEffect(Unit) {
            val window = (context as Activity).window
            
            // Enable edge-to-edge display
            WindowCompat.setDecorFitsSystemWindows(window, false)
            
            // Get the WindowInsetsController
            val windowInsetsController = WindowCompat.getInsetsController(window, view)
            
            // Configure the appearance of the status bar
            windowInsetsController.isAppearanceLightStatusBars = !darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = !darkTheme
            
            // For API 30+ (Android 11+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // Set the system bars to be transparent
                window.setDecorFitsSystemWindows(false)
                
                // Configure the system bars appearance
                val controller = window.insetsController
                controller?.let { insetsController ->
                    // Status bar appearance
                    insetsController.setSystemBarsAppearance(
                        if (!darkTheme) AndroidWindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                        AndroidWindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    
                    // Navigation bar appearance
                    insetsController.setSystemBarsAppearance(
                        if (!darkTheme) AndroidWindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0,
                        AndroidWindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                    
                    // Show the system bars
                    insetsController.show(android.view.WindowInsets.Type.systemBars())
                }
            } else {
                // For older versions, use the deprecated method as a fallback
                @Suppress("DEPRECATION")
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                
                @Suppress("DEPRECATION")
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
            }
            
            onDispose {}
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}