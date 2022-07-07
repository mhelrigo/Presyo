package com.mhelrigo.data.di

import android.content.Context
import androidx.room.Room
import com.mhelrigo.data.product.datasource.local.database.PresyoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PresyoDatabase::class.java, PresyoDatabase.NAME)
            .build()

    @Provides
    fun provideProductDao(presyoDatabase: PresyoDatabase) = presyoDatabase.productDao()
}