package com.example.serenity.uiux.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.serenity.R
import com.example.serenity.viewmodel.music.MusicViewModel
import java.util.Locale

@Composable
fun MusicScreen(
    onNavigateBack: () -> Unit,
    viewModel: MusicViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTrack = uiState.currentTrack

    // --- LOGIKA SLIDER SMOOTH ---
    val realProgress = if (uiState.totalDuration > 0) {
        uiState.currentPosition.toFloat() / uiState.totalDuration
    } else {
        0f
    }
    var isDragging by remember { mutableStateOf(false) }
    var localProgress by remember { mutableFloatStateOf(0f) }
    val finalProgress = if (isDragging) localProgress else realProgress

    // --- STATE INTERAKTIF TOMBOL (Agar bisa ganti warna pas diklik) ---
    var isShuffleActive by remember { mutableStateOf(false) }
    var isRepeatActive by remember { mutableStateOf(false) }
    var isFavoriteActive by remember { mutableStateOf(false) }

    // Palet Warna Serenity
    val BackgroundColor = Color(0xFF1E1838)
    val CardColor = Color(0xFF3B3163)
    val ActiveCardColor = Color(0xFF5E4E9E)
    val TextWhite = Color(0xFFFFFFFF)
    val TextGray = Color(0xFFA59EC2)
    val SliderTrackColor = Color(0xFF4A3E7C)

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
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        // --- TOP BAR ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack, modifier = Modifier.size(24.dp)) {
                // Ikon ArrowBack sudah diperbaiki
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextWhite
                )
            }
            Text(
                text = "Now Playing",
                fontFamily = PoppinsFont,
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- COVER ALBUM UTAMA ---
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = currentTrack?.coverUrl,
                contentDescription = "Album Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(CardColor)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- INFO LAGU ---
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
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = currentTrack?.artist ?: "Unknown Artist",
                    fontFamily = PoppinsFont,
                    color = TextGray,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // Tombol Love Interaktif (Berubah Merah saat disukai)
            IconButton(onClick = { isFavoriteActive = !isFavoriteActive }) {
                Icon(
                    imageVector = if (isFavoriteActive) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isFavoriteActive) Color(0xFFFF5252) else TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- TOMBOL PILIHAN EMOSI ---
        Text(text = "How's your mood today?", fontFamily = PoppinsFont, color = TextGray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.playSongByEmotion("happy") },
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                modifier = Modifier.weight(1f)
            ) { Text("😊 Happy", color = TextWhite, fontSize = 12.sp) }
            Button(
                onClick = { viewModel.playSongByEmotion("sad") },
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                modifier = Modifier.weight(1f)
            ) { Text("😢 Sad", color = TextWhite, fontSize = 12.sp) }
            Button(
                onClick = { viewModel.playSongByEmotion("angry") },
                colors = ButtonDefaults.buttonColors(containerColor = CardColor),
                modifier = Modifier.weight(1f)
            ) { Text("😡 Angry", color = TextWhite, fontSize = 12.sp) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- PROGRESS BAR (SMOOTH SLIDER) ---
        Slider(
            value = finalProgress,
            onValueChange = { percent ->
                isDragging = true
                localProgress = percent
            },
            onValueChangeFinished = {
                isDragging = false
                val newPosition = (localProgress * uiState.totalDuration).toLong()
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
            Text(text = formatTime(uiState.currentPosition), fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
            Text(text = formatTime(uiState.totalDuration), fontFamily = PoppinsFont, color = TextGray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- TOMBOL KONTROL PEMUTAR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol Shuffle Berubah Warna Bersih Tanpa Oranye
            Icon(
                imageVector = Icons.Filled.Shuffle,
                contentDescription = "Shuffle",
                tint = if (isShuffleActive) ActiveCardColor else TextGray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // Menghilangkan efek warna oranye/default bawaan android
                    ) { isShuffleActive = !isShuffleActive }
            )

            // Tombol Previous (Sudah diganti ikon yang benar)
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = "Previous",
                tint = TextWhite,
                modifier = Modifier
                    .size(32.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        viewModel.skipToPrevious()
                    }
            )

            // Tombol Play / Pause Utama (DIJAMIN PAS DI TENGAH)
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(ActiveCardColor)
                    .clickable { viewModel.togglePlayPause() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (uiState.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (uiState.isPlaying) "Pause" else "Play",
                    tint = TextWhite,
                    modifier = Modifier.size(36.dp) // Ukuran proposional di tengah lingkaran
                )
            }

            // Tombol Next (Sudah diganti ikon yang benar)
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = "Next",
                tint = TextWhite,
                modifier = Modifier
                    .size(32.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        viewModel.skipToNext()
                    }
            )

            // Tombol Loop/Repeat Berubah Warna Bersih Tanpa Oranye
            Icon(
                imageVector = Icons.Filled.Repeat,
                contentDescription = "Repeat",
                tint = if (isRepeatActive) ActiveCardColor else TextGray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                        isRepeatActive = !isRepeatActive
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- DAFTAR LAGU (PLAYLIST) ---
        Text(
            text = "Playlist",
            fontFamily = PoppinsFont,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(uiState.playlist) { index, track ->
                val isPlaying = track.id == currentTrack?.id

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isPlaying) ActiveCardColor else CardColor)
                        .clickable { viewModel.playTrackAt(index) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = track.coverUrl,
                        contentDescription = "Track Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SliderTrackColor)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = track.title,
                            fontFamily = PoppinsFont,
                            fontWeight = if (isPlaying) FontWeight.Bold else FontWeight.Normal,
                            color = TextWhite,
                            fontSize = 16.sp
                        )
                        Text(
                            text = track.artist,
                            fontFamily = PoppinsFont,
                            color = if (isPlaying) TextWhite.copy(alpha = 0.7f) else TextGray,
                            fontSize = 14.sp
                        )
                    }

                    if (isPlaying) {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = TextWhite)
                    }
                }
            }
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}