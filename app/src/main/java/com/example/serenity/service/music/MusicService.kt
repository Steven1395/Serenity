package com.example.serenity.service.music

import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * Service ini bertugas menjaga pemutar musik tetap hidup di latar belakang.
 * Ini juga yang akan memunculkan notifikasi pemutar musik di panel atas layar HP.
 */
class MusicService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()

        // 1. Buat mesin ExoPlayer baru di dalam Service
        val player = ExoPlayer.Builder(this).build()

        // 2. Bungkus mesin tersebut ke dalam MediaSession
        mediaSession = MediaSession.Builder(this, player).build()
    }

    // Fungsi wajib agar UI (ViewModel) bisa terhubung dan mengontrol Service ini
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    // Membersihkan memori saat Service benar-benar dimatikan
    override fun onDestroy() {
        mediaSession?.player?.release()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
}