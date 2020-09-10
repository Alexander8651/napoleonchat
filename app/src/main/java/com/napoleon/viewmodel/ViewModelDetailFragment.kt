package com.napoleon.viewmodel

import androidx.lifecycle.ViewModel
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.Repository

class ViewModelDetailFragment(private val repo: Repository) :ViewModel(){

    //set the status the parameter is the f posts that will be deleted
    suspend fun setStatusPost(post:PostSqlite){
        repo.setStatusPost(post)

    }
}