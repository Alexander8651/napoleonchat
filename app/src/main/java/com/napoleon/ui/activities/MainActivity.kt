package com.napoleon.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.napoleon.AppDatabase
import com.napoleon.R
import com.napoleon.data.Datasource
import com.napoleon.domain.RepositoryImpl
import com.napoleon.viewmodel.VMFactory
import com.napoleon.viewmodel.ViewModelMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.IO)
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    val viewmodel by viewModels<ViewModelMainActivity> {
        VMFactory(RepositoryImpl(Datasource(AppDatabase.getDatabae(this)!!)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.hostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        appBarConfiguration = AppBarConfiguration(navController.graph)



    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        uiScope.launch {
            viewmodel.getAllPost()
        }
    }
}
