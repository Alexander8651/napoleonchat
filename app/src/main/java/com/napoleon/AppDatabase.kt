package com.napoleon

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.PostDao

//this database uses the singleton
//ENtities of data base
@Database( entities = [PostSqlite::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    
    //return a postdao
    abstract fun PostDao():PostDao

    //to get instance of the same data base en in code
    companion object{

        //name of data base
        private const val DATABASE_NAME = "postsqlite"

        // init the instance in null
        @Volatile
        private var INSTANCE:AppDatabase? = null

        // function to create data base, the parameter is the context where is get the function
        fun getDatabae(context: Context):AppDatabase?{
            INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(   context.applicationContext,AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

        //function to destroy databse
        fun destroyInstance(){
            INSTANCE = null
        }

    }
}