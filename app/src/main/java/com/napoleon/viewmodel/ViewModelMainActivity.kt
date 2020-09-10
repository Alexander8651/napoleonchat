package com.napoleon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.napoleon.domain.Repository
import java.lang.Exception

class ViewModelMainActivity(private val repo:Repository):ViewModel() {

    //init to get the all post when the app is opened in the first time
    fun getAllPost() = liveData{
        try {
            //list of all post from sqlite
            val postSqlite = repo.getAllPostFromSqli()

            //when init the app is the sqlite is empty get all post from api
            if (postSqlite.isEmpty()){

                //list of all post from api
                val posts = repo.getAllPostFromApi()

                //emit the data geted
                emit(posts)
            }


        }catch (e:Exception){

        }

    }
}