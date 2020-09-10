package com.napoleon.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

//Entity of database
@Entity(tableName = "post", primaryKeys = arrayOf("userId", "id"))
@Parcelize
data class PostSqlite(

    @ColumnInfo(name = "statePost")
    var statePost: String = "",

    @ColumnInfo(name = "userId")
    val userId: Int = 0,

    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "body")
    val body: String = ""
) : Parcelable

// class to parcer the data responde from api
@Parcelize
data class PostApi(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
) : Parcelable