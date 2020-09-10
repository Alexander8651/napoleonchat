package com.napoleon.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.napoleon.ApiStatus
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewModelAllPostFragment (private val repo:Repository):ViewModel(){

    //status of api to set the animacion
    private val _status = MutableLiveData<ApiStatus>()
    val status:LiveData<ApiStatus>
        get() = _status

    //get allpost from sqlite
    fun getAllPostFromSqlite() = liveData {
        try {

            //list of all post from sqlite
            val listSqlite = repo.getAllPostFromSqli()

            Log.d("dadada", listSqlite.toString())

            //emit the data geted
            emit(listSqlite)

            // set the status of apiStatus to done
            _status.postValue(ApiStatus.DONE)
        }catch (e:Exception){
        }
    }

    //get all post after the data was geted and saved in sqlite
    fun getAllPostFromSqliteAfterfirstDownload() = liveData {
        try {

            //list of all post from sqlite
            val listSqlite = repo.getAllPostFromSqli()

            //validate that sqlite is not empty
            if (listSqlite.isNotEmpty()){
                Log.d("dadada", listSqlite.toString())

                //emit the data geted
                emit(listSqlite)

                // set the status of apiStatus to done
                _status.postValue(ApiStatus.DONE)
            }
        }catch (e:Exception){
        }
    }

    //get the data from api when is called from allpostfragment
    fun updateAllPost() = liveData{

        // set the status of apiStatus to loading
        _status.postValue(ApiStatus.LOADING)
        try {

            //list of all post from Api
            val posts = repo.getAllPostFromApi()

            //emit the data geted
            emit(posts)

            // set the status of apiStatus to done
            _status.postValue(ApiStatus.DONE)
        }catch (e: Exception){
        }
    }

    //delete post in sqlite the parameter is the post that will be deleted
    fun postDelete(pos:PostSqlite){

        //ini coroutine
        viewModelScope.launch{
            //send the post to deleting
            repo.deletePost(pos)
        }
    }

    //delete all post in sqlite the parameter is the list of posts that will be deleted
    fun allPostDelete(postList:List<PostSqlite>){

        //ini coroutine
        viewModelScope.launch{

            //send the list to deleting
            repo.deleteAllPost(postList)
        }

    }


}