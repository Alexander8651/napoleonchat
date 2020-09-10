package com.napoleon.domain

import com.napoleon.data.model.PostApi
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface WebService {

    // get petition from api
    @GET("posts")
    fun getAllPost():Deferred<List<PostApi>>
}