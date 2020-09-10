package com.napoleon.domain

import com.napoleon.data.Datasource
import com.napoleon.data.model.PostSqlite


class RepositoryImpl(private val datasource: Datasource):Repository {

    override suspend fun getAllPostFromApi( ) {
        datasource.getPostFromApiandSaveInSqlite()
    }

    override suspend fun getAllPostFromSqli(): List<PostSqlite> {
        return datasource.getALlPostFromSqlite()
    }

    override suspend fun setStatusPost(post: PostSqlite) {
        datasource.setStatusPost(post)
    }

    override suspend fun getFavorites(): List<PostSqlite> {
        return datasource.getFavorites()
    }

    override suspend fun deletePost(post: PostSqlite) {
        datasource.deletePost(post)
    }

    override suspend fun deleteAllPost(postList: List<PostSqlite>) {
        datasource.deleteAllPost(postList)
    }

}