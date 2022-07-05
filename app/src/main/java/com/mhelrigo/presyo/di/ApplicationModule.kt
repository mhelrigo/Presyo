package com.mhelrigo.presyo.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideFirebaseDatabase() = Firebase.database

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context) =
        context.getSharedPreferences("com.mhelrigo.presyo.sharedPrefrence", Context.MODE_PRIVATE)
}