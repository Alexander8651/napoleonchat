package com.napoleon

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.PostDao


@Database( entities = [PostSqlite::class], version = 1)
abstract class AppDatabase:RoomDatabase(){

    abstract fun PostDao():PostDao

    companion object{


        private const val DATABASE_NAME = "postsqlite"

        @Volatile
        private var INSTANCE:AppDatabase? = null

        fun getDatabae(context: Context):AppDatabase?{
            INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(   context.applicationContext,AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }
}