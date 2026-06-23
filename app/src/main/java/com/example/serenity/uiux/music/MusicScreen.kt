package com.example.serenity.uiux.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.serenity.R
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import com.example.serenity.viewmodel.music.MusicViewModel
import java.util.Locale

@Composable
fun MusicScreen(
    onNavigateBack: () -> Unit,
    viewModel: MusicViewModel = androidx.lifecycle.viewmodel.compose.viewModel() // Hubungkan ke ViewModel
) {
    // Memantau perubahan State UI dari ViewModel secara real-time
    val uiState by viewModel.uiState.collectAsState()
    val currentTrack = uiState.currentTrack

    // Menghitung progress slider (0.0f sampai 1.0f)
    val progress = if (uiState.totalDuration > 0) {
        uiState.currentPosition.toFloat() / uiState.totalDuration
    } else {
        0f
    }

    // Mengambil data lagu berikutnya untuk bagian "Up Next"
    val currentIndex = uiState.playlist.indexOf(currentTrack)
    val nextTrack = uiState.playlist.getOrNull(currentIndex + 1)

    // Definisi Warna Tema Serenity
    val BackgroundColor = Color(0xFF2E2559)
    val CardColor = Color(0xFF4C4378)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFB3ADCC)
    val SliderTrackColor = Color(0xFF5E5686)

    // Definisi Font Poppins
    val PoppinsFont = try {
        FontFamily(
            Font(R.font.poppins_regular, FontWeight.Normal),
            Font(R.font.poppins_bold, FontWeight.Bold)
        )
    } catch (_: Exception) {
        FontFamily.Default
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 24.dp, vertical = 48.dp)
    ) {
        // --- TOP BAR (Tombol Back) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextWhite
                )
            }
            Text(
                text = "Back",
                fontFamily = PoppinsFont,
                color = TextWhite,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- COVER ALBUM UTAMA ---
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .clip(CircleShape)
                    .background(CardColor)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- INFO LAGU DARI VIEWMODEL ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = currentTrack?.title ?: "No Track Selected",
                    fontFamily = PoppinsFont,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 24.sp
                )
                Text(
                    text = currentTrack?.artist ?: "Unknown Artist",
                    fontFamily = PoppinsFont,
                    color = TextGray,
                    fontSize = 14.sp
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like", tint = TextWhite)
                }
                IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Filled.Download, contentDescription = "Download", tint = TextWhite)
                }
                IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = "Share", tint = TextWhite)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- PROGRESS BAR (Slider Berjalan Otomatis) ---
        Slider(
            value = progress,
            onValueChange = { percent ->
                val newPosition = (percent * uiState.totalDuration).toLong()
                viewModel.seekToPosition(newPosition)
            },
            colors = SliderDefaults.colors(
                thumbColor = TextWhite,
                activeTrackColor = TextWhite,
                inactiveTrackColor = SliderTrackColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Menggunakan fungsi formatTime untuk durasi real-time
            Text(text = formatTime(uiState.currentPosition), fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
            Text(text = formatTime(uiState.totalDuration), fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- TOMBOL KONTROL PEMUTAR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🔀", color = TextWhite, fontSize = 20.sp, modifier = Modifier.clickable { })

            // Tombol Previous
            Text(
                text = "⏮",
                color = TextWhite,
                fontSize = 24.sp,
                modifier = Modifier.clickable { viewModel.skipToPrevious() }
            )

            // Tombol Play / Pause Utama
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(TextWhite)
                    .clickable { viewModel.togglePlayPause() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (uiState.isPlaying) "⏸" else "▶",
                    color = BackgroundColor,
                    fontSize = 24.sp
                )
            }

            // Tombol Next
            Text(
                text = "⏭",
                color = TextWhite,
                fontSize = 24.sp,
                modifier = Modifier.clickable { viewModel.skipToNext() }
            )

            Text("🔁", color = TextWhite, fontSize = 20.sp, modifier = Modifier.clickable { })
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- UP NEXT (Dinamis Berdasarkan Antrean Lagu) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Up Next", fontFamily = PoppinsFont, color = TextGray, fontSize = 16.sp)
            Text(text = "Queue >", fontFamily = PoppinsFont, color = TextWhite, fontSize = 14.sp)
        }

        // Tampilkan info lagu berikutnya jika ada di playlist
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CardColor)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SliderTrackColor)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = nextTrack?.title ?: "End of Playlist",
                    fontFamily = PoppinsFont,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 16.sp
                )
                Text(
                    text = nextTrack?.artist ?: "-",
                    fontFamily = PoppinsFont,
                    color = TextGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * Fungsi pembantu untuk mengubah milidetik (Long) menjadi format teks menit:detik (00:00)
 */
fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}