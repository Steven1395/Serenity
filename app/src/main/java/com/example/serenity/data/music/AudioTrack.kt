package com.example.serenity.data.music

/**
 * Model data ini mewakili satu buah trek audio.
 * @param id: ID unik untuk lagu tersebut.
 * @param title: Judul lagu (misal: "Hujan Deras").
 * @param artist: Nama pembuat/kategori (misal: "Nature Sounds").
 * @param audioUrl: Lokasi file, bisa berupa URL internet (http...) atau lokasi lokal (android.resource://...).
 */
data class AudioTrack(
    val id: String,
    val title: String,
    val artist: String,
    val audioUrl: String
)