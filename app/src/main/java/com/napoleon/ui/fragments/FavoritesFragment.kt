package com.napoleon.ui.fragments

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.napoleon.AppDatabase
import com.napoleon.R
import com.napoleon.data.Datasource
import com.napoleon.databinding.FragmentFavoritesBinding
import com.napoleon.domain.RepositoryImpl
import com.napoleon.ui.adapters.PostAdapter
import com.napoleon.viewmodel.VMFactory
import com.napoleon.viewmodel.ViewMoldelFavoritePost


class FavoritesFragment : Fragment() {

    //init viewmodel of favorites
    val viewmodel by viewModels<ViewMoldelFavoritePost> { VMFactory(
        RepositoryImpl(
            Datasource(
        AppDatabase.getDatabae(requireContext().applicationContext)!!)
        )
    ) }

    //declared binding and adapterfavorites
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapterFavorites: PostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init binding
        binding = FragmentFavoritesBinding.bind(view)

        // set the lifecycleowner
        binding.lifecycleOwner = this

        //init the adapter of recycelrview
        adapterFavorites = PostAdapter()

        //apply change to the recyclerview
        binding.rvFavorites.apply {
            adapter = adapterFavorites
        }
        //get data from favorites
        viewmodel.getFavorites().observe(viewLifecycleOwner, Observer {
            adapterFavorites.submitList(it)
        })

        //ini the function
        swipePost()
    }

    //this function is to swipe and delete the items in the recyclerview

    fun swipePost(){

        //callback to informate when a item is swiped
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,

                //Indicate the actions when swipe right or left
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                //set visibility of animation
                binding.statusDeleting.visibility = View.VISIBLE

                // is desplaced when the item is deleted
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()

                //position of item to delete
                val position = viewHolder.adapterPosition

                //get the item in the position to delete
                val deletePost = adapterFavorites.currentList[position]

                //init deleting the post
                viewmodel.postDelete(deletePost)

                //init the animation
                deletingAnimated()

            }
        }

        // init touchhlper
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        //indicate which is the recyvlerview
        itemTouchHelper.attachToRecyclerView(binding.rvFavorites)
    }

    //this function init the animation
    fun deletingAnimated(){

        //init the animation
        binding.deleting.playAnimation()

        //listenner of states of animation
        binding.deleting.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                Log.e("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {

                // set the anomation to invisible when the animation is ended
                binding.statusDeleting.visibility = View.INVISIBLE

                //call data after delete a post
                viewmodel.getFavorites().observe(viewLifecycleOwner, {
                    adapterFavorites.submitList(it)
                })
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.e("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.e("Animation:", "repeat")


            }
        })
    }

}