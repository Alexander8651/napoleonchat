package com.napoleon.domain

import androidx.room.*
import com.napoleon.data.model.PostSqlite

@Dao
interface PostDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAllPost(listPost:List<PostSqlite>)

    @Query("SELECT * FROM post ")
    suspend fun getAllPost():List<PostSqlite>

    @Update
    suspend fun setStatusPost( post:PostSqlite)

}