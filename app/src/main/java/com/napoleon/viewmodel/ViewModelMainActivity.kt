package com.napoleon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.napoleon.domain.Repository
import java.lang.Exception

class ViewModelMainActivity(private val repo:Repository):ViewModel() {

    fun getAllPost() = liveData{
        try {
            val posts = repo.getAllPostFromApi()
            emit(posts)
        }catch (e:Exception){

        }

    }
}