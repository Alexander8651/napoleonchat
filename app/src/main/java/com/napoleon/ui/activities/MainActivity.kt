package com.napoleon.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

class MainActivity : AppCompatActivity() {

    //declare navcontroller and appconfig
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    //ini viewmodel
    val viewmodel by viewModels<ViewModelMainActivity> {
        VMFactory(RepositoryImpl(Datasource(AppDatabase.getDatabae(this)!!)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init navcontroller
        navController = findNavController(R.id.hostFragment)

        //Set navigation ui
        NavigationUI.setupActionBarWithNavController(this, navController)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        //Init the call to api when the app is open in firt time
        viewmodel.getAllPost().observe(this, {  })




    }

    override fun onSupportNavigateUp(): Boolean {
        //setup the navigationup
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }


}
