package com.napoleon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.napoleon.data.model.PostSqlite
import com.napoleon.domain.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class ViewMoldelFavoritePost(private val repo:Repository):ViewModel() {

    //init to get the all favorites post
    fun getFavorites() = liveData {
        try {

            //list of favorite post from sqlite
            val favorites = repo.getFavorites()

            //emit the data geted
            emit(favorites)

        }catch (e:Exception){

        }
    }

    //delete post in sqlite the parameter is the post that will be deleted
    fun postDelete(pos: PostSqlite){

        //ini coroutine
        viewModelScope.launch{

            //send the post to deleting
            repo.deletePost(pos)
        }

    }


}