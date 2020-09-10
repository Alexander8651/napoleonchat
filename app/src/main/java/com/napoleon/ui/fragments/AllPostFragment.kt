package com.napoleon.ui.fragments

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.napoleon.AppDatabase
import com.napoleon.R
import com.napoleon.data.Datasource
import com.napoleon.data.model.PostSqlite
import com.napoleon.databinding.FragmentAllPostBinding
import com.napoleon.domain.RepositoryImpl
import com.napoleon.ui.adapters.PostAdapter
import com.napoleon.viewmodel.VMFactory
import com.napoleon.viewmodel.ViewModelAllPostFragment


class AllPostFragment : Fragment() {

    //init viewmodel of all post
    val viewmodel by viewModels<ViewModelAllPostFragment> {
        VMFactory(RepositoryImpl(Datasource(AppDatabase.getDatabae(requireContext().applicationContext)!!)))
    }

    //declare binding and adapter for the recyclerview as global
    private lateinit var binding: FragmentAllPostBinding
    private lateinit var adapterRV:PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init binding
        binding = FragmentAllPostBinding.bind(view)

        // set the lifecycleowner
        binding.lifecycleOwner = this

        //init the viewmodel of xml to viewmodel of all post
        binding.viewmodel = viewmodel

        //init the adapter of recycelrview
        adapterRV = PostAdapter()

        //apply change to the recyclerview
        binding.rvAllPost.apply {
            adapter = adapterRV
        }

        //ini the functions
        swipePost()
        observers()
    }

    //this function has all observers
    fun observers(){

        //Observe the state from the data source to get the data from sqlite when all post are saved
        Datasource.stateofSave.observe(viewLifecycleOwner, Observer {

            if (it) {

                //observe the data from sqlite
                viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, {
                    adapterRV.submitList(it)

                })
            }

        })

        //observe the data from sqlite is use when the internet service off
        viewmodel.getAllPostFromSqliteAfterfirstDownload().observe(viewLifecycleOwner, Observer {
            adapterRV.submitList(it)

        })
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
                val deletePost = adapterRV.currentList[position]

                //init deleting the post
                viewmodel.postDelete(deletePost)

                //init the animation
                deletingAnimated()

            }
        }

        // init touchhlper
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        //indicate which is the recyvlerview
        itemTouchHelper.attachToRecyclerView(binding.rvAllPost)
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
                viewmodel.getAllPostFromSqliteAfterfirstDownload().observe(viewLifecycleOwner, {
                    adapterRV.submitList(it)
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


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //inflate the menu
        inflater.inflate(R.menu.allpostmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            //Navigate to favorites fragment when item is clicked
            R.id.favoritesItem -> {
                findNavController().navigate(R.id.action_allPostFragment_to_favoritesFragment)
                true
            }

            //init updating the data from api
            R.id.updateItem -> {
                viewmodel.updateAllPost().observe(viewLifecycleOwner, {

                    //displaced when start to updating data
                    Toast.makeText(requireContext(), "Updating", Toast.LENGTH_SHORT).show()

                    //Observe the state from the data source to get the data from sqlite when all post are saved
                    Datasource.stateofSave.observe(viewLifecycleOwner, Observer {
                        if (it) {
                            viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, {
                                adapterRV.submitList(it)
                                Log.d("respuestaactualizada", "wqwq")

                            })
                        }
                    })
                })

                true
            }

            //delete all post
            R.id.deleteAll -> {

                //set the animation to visible
                binding.statusDeleting.visibility = View.VISIBLE

                //init the animation
                deletingAnimated()

                //all the post to delete
                val listPost = adapterRV.currentList

                //init to delete all post
                viewmodel.allPostDelete(listPost)

                //delay to wait that data is deleted
                val runner = Runnable {
                    viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, {
                        adapterRV.submitList(it)
                        Log.d("respuestaactualizada",it.toString())
                    })
                }
                val handler = android.os.Handler()
                handler.postDelayed(runner, 500)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }



}