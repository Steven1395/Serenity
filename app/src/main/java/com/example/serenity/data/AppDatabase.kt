package com.example.serenity.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.serenity.data.journal.JournalDao
import com.example.serenity.data.journal.JournalEntity
import com.example.serenity.data.journal.SleepDao
import com.example.serenity.data.journal.SleepEntity

// PERBARUAN: Ubah version menjadi 3 karena ada kolom 'sleepQuality' baru di SleepEntity
@Database(entities = [JournalEntity::class, SleepEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao
    abstract fun sleepDao(): SleepDao

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
                    // Tetap pertahankan ini agar database otomatis meriset tabel lama tanpa bikin aplikasi crash
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}