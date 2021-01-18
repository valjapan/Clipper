package com.valjapan.clipper

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideHistoryDB(@ApplicationContext context: Context): HistoryDatabase {
        return Room
            .databaseBuilder(
                context,
                HistoryDatabase::class.java,
                "database"
            )
//            .createFromAsset("history.db")
            .build()
    }
}