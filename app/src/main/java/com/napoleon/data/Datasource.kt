package com.napoleon.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "post")
@Parcelize
data class Datasource(
    var statePost: String = "",
    val userI: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
) : Parcelable