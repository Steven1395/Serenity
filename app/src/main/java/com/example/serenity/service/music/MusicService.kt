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

        val player = ExoPlayer.Builder(this).build()

        mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    // --- TAMBAHAN BARU DI SINI ---
    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player

        if (player != null) {
            player.stop()
        }

        stopSelf()

        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        mediaSession?.player?.release()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
}