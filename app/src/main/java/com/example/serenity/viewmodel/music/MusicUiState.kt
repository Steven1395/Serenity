package com.example.serenity.viewmodel.music


import com.example.serenity.data.music.AudioTrack

/**
 * Data class untuk menampung seluruh status (state) UI pemutar musik.
 */
data class MusicUiState(
    val currentTrack: AudioTrack? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val playlist: List<AudioTrack> = emptyList()
)