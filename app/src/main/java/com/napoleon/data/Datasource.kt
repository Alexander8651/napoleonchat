package com.napoleon.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.napoleon.AppDatabase
import com.napoleon.data.model.PostSqlite
import com.napoleon.data.netwok.api.RetrofitService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Datasource (private val appDatabase: AppDatabase){

    companion object{
        private val _stateofSave = MutableLiveData<Boolean>()
        val stateofSave:LiveData<Boolean>
            get() = _stateofSave
    }

    suspend fun getPostFromApiandSaveInSqlite(){
        val post = RetrofitService.retrofitService.getAllPost().await()
        val listPostSqlite = post.map {
            PostSqlite(
                "unread",
                it.userId,
                it.id,
                it.title,
                it.body
            )
        }

        appDatabase.PostDao().saveAllPost(listPostSqlite)
        GlobalScope.launch {
            _stateofSave.postValue(true)
        }


        //Log.d("SAVEALLPOS", saved.toString() )

    }

    suspend fun getALlPostFromSqlite():List<PostSqlite>{

        return appDatabase.PostDao().getAllPost()
    }

    suspend fun setStatusPost(postSqlite: PostSqlite){
        appDatabase.PostDao().setStatusPost(postSqlite)
    }

}