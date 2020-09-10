package com.napoleon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.napoleon.domain.Repository
import java.lang.Exception

class ViewModelAllPostFragment (private val repo:Repository):ViewModel(){

    fun getAllPostFromSqlite() = liveData {

        try {

            val listSqlite = repo.getAllPostFromSqli()

            Log.d("dadada", listSqlite.toString())
            emit(listSqlite)

        }catch (e:Exception){

        }

    }

    fun updateAllPost() = liveData{

        try {
            val posts = repo.getAllPostFromApi()
            emit(posts)
        }catch (e: Exception){

        }

    }

}