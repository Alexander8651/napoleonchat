package com.napoleon.viewmodel

import androidx.lifecycle.ViewModel
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.Repository

class ViewModelDetailFragment(private val repo: Repository) :ViewModel(){

    suspend fun setStatusPost(post:PostSqlite){
        repo.setStatusPost(post)

    }
}