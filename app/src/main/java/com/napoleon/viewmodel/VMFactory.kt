package com.napoleon.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.napoleon.domain.Repository

class VMFactory ( private val repo: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        //return the constructos with the parameters required
        return modelClass.getConstructor(Repository::class.java).newInstance(repo)
    }

}