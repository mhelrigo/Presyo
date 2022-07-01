package com.mhelrigo.data.common

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class PresyoDatabase : RoomDatabase(){
}