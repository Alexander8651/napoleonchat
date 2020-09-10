package com.napoleon.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.napoleon.AppDatabase
import com.napoleon.R
import com.napoleon.data.Datasource
import com.napoleon.data.model.PostSqlite
import com.napoleon.databinding.FragmentDetailsPostBinding
import com.napoleon.domain.RepositoryImpl
import com.napoleon.viewmodel.VMFactory
import com.napoleon.viewmodel.ViewModelDetailFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsPostFragment : Fragment() {

    //init viewmodel of details
    val viewmodel by viewModels<ViewModelDetailFragment> {VMFactory(RepositoryImpl(Datasource(
        AppDatabase.getDatabae(requireContext().applicationContext)!!)))  }

    //declarer postsqlite and databinding
    private lateinit var post:PostSqlite
    private lateinit var binding: FragmentDetailsPostBinding

    //jon of coroutine
    val job = Job()
    // scope of coroutine with background dispatcher
    val uiScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get the post send from recyclerview
        requireArguments().apply {

            //init the post with the data obtein
            post = getParcelable("post")!!
        }

        Log.d("Parcelable", post.toString() )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init binding
        binding = FragmentDetailsPostBinding.bind(view)

        //set bind postdetail to bind the view with data
        binding.postdetail = post

        ////ini the functions
        setStatusPostReaded(post)
        setStatusPostFavorites(post)



    }

    //set the status of post when the favorite boton is clicked
    private fun setStatusPostFavorites(post: PostSqlite) {
        binding.saveinFavorites.setOnClickListener {

            //ini new post with new status
            val postDetailtoFavorites = PostSqlite("favorites", post.userId, post.id, post.title, post.body)

            //init the corroutine to set the status
            uiScope.launch {

                //init function to set status
                viewmodel.setStatusPost(postDetailtoFavorites)
            }

            //displaced when the pos has benn added to favorites
            Toast.makeText(requireContext(), "Add to Favorites", Toast.LENGTH_SHORT).show()
            Log.d("favoritos", postDetailtoFavorites.toString())
        }
    }

    //set the estatus to readed
    fun setStatusPostReaded(post: PostSqlite){

        //ini new post with new status
        val postDetails = PostSqlite("readed", post.userId, post.id, post.title, post.body)

        //init the corroutine to set the status, if post is not favorite status
        uiScope.launch {

            if (post.statePost != "favorites"){
                viewmodel.setStatusPost(postDetails)
            }

        }
    }



}