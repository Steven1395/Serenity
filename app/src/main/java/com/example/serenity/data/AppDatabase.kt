package com.example.serenity.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.serenity.data.journal.JournalDao
import com.example.serenity.data.journal.JournalEntity
import com.example.serenity.data.journal.SleepDao
import com.example.serenity.data.journal.SleepEntity
// 1. Import tabel dan perintah untuk fitur Chat
import com.example.serenity.data.natunai.ChatDao
import com.example.serenity.data.natunai.ChatEntity

// 2. PERBARUAN: Tambahkan ChatEntity::class dan ubah version menjadi 4
@Database(
    entities = [JournalEntity::class, SleepEntity::class, ChatEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao
    abstract fun sleepDao(): SleepDao
    // 3. Tambahkan fungsi abstract untuk ChatDao di sini
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "serenity_database"
                )
                    // Tetap pertahankan ini agar database otomatis mereset tabel lama tanpa bikin aplikasi crash
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}