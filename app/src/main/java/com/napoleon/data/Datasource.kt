package com.napoleon.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.napoleon.AppDatabase
import com.napoleon.data.model.PostSqlite
import com.napoleon.data.netwok.api.RetrofitService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Handler

//this class obtein all data from api and sqlite

class Datasource (private val appDatabase: AppDatabase){

    companion object{

        //user to information that all post obtein from api are saved in sqlite
        private val _stateofSave = MutableLiveData<Boolean>()
        val stateofSave:LiveData<Boolean>
            get() = _stateofSave


    }



    suspend fun getPostFromApiandSaveInSqlite(){

        GlobalScope.launch {
            _stateofSave.postValue(false)
        }

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

        //coroutine to global scope to set de data in _stateofSave
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

    suspend fun getFavorites():List<PostSqlite>{
        return appDatabase.PostDao().getAllFavorites()
    }

    suspend fun deletePost(post: PostSqlite){
        appDatabase.PostDao().deletePost(post)
    }

    suspend fun deleteAllPost(postList: List<PostSqlite>){
        appDatabase.PostDao().deleteAllPost(postList)



    }



}