package com.napoleon.domain

import com.napoleon.data.Datasource
import com.napoleon.data.model.PostSqlite


interface Repository {

    suspend fun getAllPostFromApi()
    suspend fun getAllPostFromSqli():List<PostSqlite>
    suspend fun setStatusPost(post: PostSqlite)
    suspend fun getFavorites():List<PostSqlite>
    suspend fun deletePost(post:PostSqlite)
    suspend fun deleteAllPost(postList:List<PostSqlite>)
}