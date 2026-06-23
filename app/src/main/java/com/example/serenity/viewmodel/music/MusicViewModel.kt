package com.example.serenity.viewmodel.music

import android.app.Application
import android.content.ComponentName
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.serenity.data.music.AudioTrack
import com.example.serenity.service.music.MusicService
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application), Player.Listener {

    private val _uiState = MutableStateFlow(MusicUiState())
    val uiState: StateFlow<MusicUiState> = _uiState.asStateFlow()

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var player: Player? = null

    init {
        initializeController()
    }

    private fun initializeController() {
        val sessionToken = SessionToken(
            getApplication(),
            ComponentName(getApplication(), MusicService::class.java)
        )

        controllerFuture = MediaController.Builder(getApplication(), sessionToken).buildAsync()

        controllerFuture?.addListener(
            Runnable {
                try {
                    player = controllerFuture?.get()
                    player?.addListener(this@MusicViewModel)

                    loadSamplePlaylist()
                    monitorPlaybackProgress()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            ContextCompat.getMainExecutor(getApplication())
        )
    }

    private fun loadSamplePlaylist() {
        val sampleTracks = listOf(
            AudioTrack("1", "Go Sleep", "The Sleeping Lofi", "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"),
            AudioTrack("2", "Young", "The Chainsmokers", "https://storage.googleapis.com/exoplayer-test-media-0/big_buck_bunny.mp3")
        )

        _uiState.update {
            it.copy(playlist = sampleTracks, currentTrack = sampleTracks.firstOrNull())
        }

        player?.let { p ->
            p.clearMediaItems()
            sampleTracks.forEach { track ->
                p.addMediaItem(MediaItem.fromUri(track.audioUrl))
            }
            p.prepare()
        }
    }

    fun togglePlayPause() {
        player?.let { p ->
            if (p.isPlaying) p.pause() else p.play()
        }
    }

    fun skipToNext() {
        if (player?.hasNextMediaItem() == true) {
            player?.seekToNext()
        }
    }

    fun skipToPrevious() {
        if (player?.hasPreviousMediaItem() == true) {
            player?.seekToPrevious()
        }
    }

    fun seekToPosition(position: Long) {
        player?.seekTo(position)
    }

    private fun monitorPlaybackProgress() {
        viewModelScope.launch {
            while (true) {
                player?.let { p ->
                    if (p.isPlaying) {
                        _uiState.update {
                            it.copy(
                                currentPosition = p.currentPosition,
                                totalDuration = p.duration.coerceAtLeast(0L)
                            )
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _uiState.update { it.copy(isPlaying = isPlaying) }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        val currentIndex = player?.currentMediaItemIndex ?: 0
        val track = _uiState.value.playlist.getOrNull(currentIndex)
        _uiState.update { it.copy(currentTrack = track) }
    }

    override fun onCleared() {
        super.onCleared()
        player?.removeListener(this)
        controllerFuture?.let { MediaController.releaseFuture(it) }
    }
}