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

    val viewmodel by viewModels<ViewModelDetailFragment> {VMFactory(RepositoryImpl(Datasource(
        AppDatabase.getDatabae(requireContext().applicationContext)!!)))  }

    private lateinit var post:PostSqlite

    private lateinit var binding: FragmentDetailsPostBinding

    val job = Job()

    val uiScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().apply {
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

        binding = FragmentDetailsPostBinding.bind(view)

        binding.postdetail = post

        setStatusPostReaded(post)

        setStatusPostFavorites(post)



    }

    private fun setStatusPostFavorites(post: PostSqlite) {
        binding.saveinFavorites.setOnClickListener {
            val postDetailtoFavorites = PostSqlite("favorites", post.userId, post.id, post.title, post.body)

            uiScope.launch {
                viewmodel.setStatusPost(postDetailtoFavorites)
            }
            Toast.makeText(requireContext(), "Add to Favorites", Toast.LENGTH_SHORT).show()
            Log.d("favoritos", postDetailtoFavorites.toString())
        }
    }

    fun setStatusPostReaded(post: PostSqlite){

        val postDetails = PostSqlite("readed", post.userId, post.id, post.title, post.body)

        uiScope.launch {

            if (post.statePost != "favorites"){
                viewmodel.setStatusPost(postDetails)
            }

        }
    }



}