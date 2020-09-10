package com.napoleon

import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter


// set the animation when the app is opened
@BindingAdapter("ApiStatus")
fun bindStatus(statusRelativeLayout: RelativeLayout, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusRelativeLayout.visibility = View.VISIBLE
        }
        ApiStatus.ERROR -> {
            statusRelativeLayout.visibility = View.VISIBLE
        }
        ApiStatus.DONE -> {
            statusRelativeLayout.visibility = View.INVISIBLE
        }
    }
}

//class to set the status to indicate to the bindingadapter what to do
enum class ApiStatus {LOADING,DONE, ERROR}


