package com.example.serenity.uiux.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =====================================================================================
// PALET WARNA
// =====================================================================================
// Tiga warna dasar berikut TIDAK diubah (nama & value sama persis seperti kode original)
val TextDarkPurple = Color(0xFF2B2250)
val LightLavenderBg = Color(0xFFD2CFFC)
val ProfileLavender = Color(0xFF8884D8)

// Warna turunan tambahan, hanya untuk gradasi & aksen premium.
// Tidak menyentuh tiga warna dasar di atas maupun teks/logic yang dikunci.
private val BgGradientTop = Color(0xFFF8F6FF)
private val BgGradientBottom = Color(0xFFFFFFFF)

private val HeaderGradientStart = Color(0xFF7A74CC)
private val HeaderGradientEnd = Color(0xFF433C8C)

// Setiap fitur utama punya identitas gradasi sendiri agar grid terasa hidup,
// tetap dalam satu keluarga warna ungu/lavender supaya tetap menyatu
private val QuestionnaireStart = Color(0xFFC9C5FA)
private val QuestionnaireEnd = Color(0xFF8884D8)

private val SleepStart = Color(0xFF4A4390)
private val SleepEnd = Color(0xFF1F1A46)

private val AiStart = Color(0xFFFFC3DA)
private val AiEnd = Color(0xFFFF8FAE)

private val MusicStart = Color(0xFFA9EDD4)
private val MusicEnd = Color(0xFF5FC7A8)

private val UtilityIconBg = Color(0xFFEFECFC)

// =====================================================================================
// DASHBOARD SCREEN
// Signature fungsi & parameter SAMA PERSIS seperti kode original
// =====================================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToMusic: () -> Unit,
    onNavigateToAi: () -> Unit,
    onNavigateToJournal: () -> Unit,
    onNavigateToQuestionnaire: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(BgGradientTop, BgGradientBottom)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // --- HEADER: gradasi penuh lebar, sudut bawah melengkung, search bar melayang ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(colors = listOf(HeaderGradientStart, HeaderGradientEnd)),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(horizontal = 24.dp)
                    .padding(top = 56.dp, bottom = 28.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Serenity",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .shadow(elevation = 6.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.95f), LightLavenderBg)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil",
                            tint = HeaderGradientEnd,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // --- SEARCH BAR: pill shape, melayang dengan shadow halus di atas header ---
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari", color = TextDarkPurple.copy(alpha = 0.5f)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextDarkPurple.copy(alpha = 0.6f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(28.dp), clip = false),
                    shape = RoundedCornerShape(28.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {

                // --- SUB-HEADER: Dashboard Utama ---
                Text(
                    text = "Dashboard Utama",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDarkPurple
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- GRID 2 KOLOM UNTUK 4 FITUR UTAMA ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    DashboardFeatureCard(
                        icon = Icons.Default.List,
                        title = "Kuisioner Aktivitas",
                        gradientColors = listOf(QuestionnaireStart, QuestionnaireEnd),
                        contentColor = TextDarkPurple,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToQuestionnaire()
                    }
                    DashboardFeatureCard(
                        icon = Icons.Default.Star,
                        title = "Pantauan Tidur",
                        gradientColors = listOf(SleepStart, SleepEnd),
                        contentColor = Color.White,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToJournal()
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    DashboardFeatureCard(
                        icon = Icons.Default.Face,
                        title = "Tanya CherryAI",
                        gradientColors = listOf(AiStart, AiEnd),
                        contentColor = TextDarkPurple,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToAi()
                    }
                    DashboardFeatureCard(
                        icon = Icons.Default.PlayArrow,
                        title = "Musik Serenity",
                        gradientColors = listOf(MusicStart, MusicEnd),
                        contentColor = TextDarkPurple,
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigateToMusic()
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))

                // --- DAFTAR MENU LAINNYA (Pengaturan, Bantuan, Tentang Kami) ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(22.dp), clip = false),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    DashboardMenuItem(icon = Icons.Default.Settings, title = "Pengaturan") {
                        // TODO: Navigasi ke Pengaturan
                    }
                    HorizontalDivider(
                        color = LightLavenderBg.copy(alpha = 0.4f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 70.dp)
                    )

                    DashboardMenuItem(icon = Icons.Default.Info, title = "Bantuan") {
                        // TODO: Navigasi ke Bantuan
                    }
                    HorizontalDivider(
                        color = LightLavenderBg.copy(alpha = 0.4f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 70.dp)
                    )

                    DashboardMenuItem(icon = Icons.Default.Info, title = "Tentang Kami") {
                        // TODO: Navigasi ke Tentang Kami
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

// =====================================================================================
// KARTU FITUR UTAMA (grid 2 kolom)
// Komponen BARU, melengkapi DashboardMenuItem — bukan pengganti, sehingga tidak
// menyentuh signature yang dikunci
// =====================================================================================
@Composable
fun DashboardFeatureCard(
    icon: ImageVector,
    title: String,
    gradientColors: List<Color>,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp), clip = false)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(colors = gradientColors))
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.28f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = title,
            color = contentColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.sp
        )
    }
}

// =====================================================================================
// MENU ITEM (daftar sekunder: Pengaturan, Bantuan, Tentang Kami)
// Signature & parameter SAMA PERSIS seperti kode original — hanya bagian visual
// di dalam body-nya yang didesain ulang
// =====================================================================================
@Composable
fun DashboardMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(UtilityIconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = TextDarkPurple,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = title,
            color = TextDarkPurple,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = TextDarkPurple.copy(alpha = 0.35f),
            modifier = Modifier.size(20.dp)
        )
    }
}
