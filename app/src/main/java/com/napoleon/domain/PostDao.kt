package com.napoleon.domain

import androidx.room.*
import com.napoleon.data.model.PostSqlite

@Dao
interface PostDao{

    @Insert
    fun insert(post:PostSqlite)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAllPost(listPost:List<PostSqlite>)

    @Query("SELECT * FROM post ")
    suspend fun getAllPost():List<PostSqlite>

    @Update
    suspend fun setStatusPost( post:PostSqlite)

    @Query("SELECT * FROM post WHERE statePost == 'favorites'")
    suspend fun getAllFavorites():List<PostSqlite>

    @Delete
    suspend fun deletePost(post:PostSqlite)

    @Delete
    suspend fun deleteAllPost(allPost:List<PostSqlite>)

    @Query ("SELECT * FROM post")
    fun getthisPost():PostSqlite?

}