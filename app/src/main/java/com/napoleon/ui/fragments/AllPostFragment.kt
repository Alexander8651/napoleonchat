package com.napoleon.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.napoleon.AppDatabase
import com.napoleon.R
import com.napoleon.data.Datasource
import com.napoleon.databinding.FragmentAllPostBinding
import com.napoleon.domain.RepositoryImpl
import com.napoleon.ui.adapters.PostAdapter
import com.napoleon.viewmodel.VMFactory
import com.napoleon.viewmodel.ViewModelAllPostFragment

class AllPostFragment : Fragment() {

    val viewmodel by viewModels<ViewModelAllPostFragment> {
        VMFactory(RepositoryImpl(Datasource(AppDatabase.getDatabae(requireContext().applicationContext)!!)))
    }
    private lateinit var binding: FragmentAllPostBinding
    private lateinit var adapterRV:PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAllPostBinding.bind(view)
        binding.lifecycleOwner = this

        binding.viewmodel = viewmodel

        adapterRV = PostAdapter()

        binding.rvAllPost.apply {
            adapter = adapterRV
        }


        Datasource.stateofSave.observe(viewLifecycleOwner, Observer {

            if (it == true){
                viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, Observer {
                    adapterRV.submitList(it)

                })
            }

        })

        viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, Observer {
            adapterRV.submitList(it)

        })

    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.allpostmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritesItem -> {findNavController().navigate(R.id.action_allPostFragment_to_favoritesFragment)
            true}

            R.id.updateItem -> {
                viewmodel.updateAllPost()
                viewmodel.getAllPostFromSqlite().observe(viewLifecycleOwner, Observer {
                    adapterRV.submitList(it)
                    Toast.makeText(requireContext(), "Updating Post", Toast.LENGTH_SHORT).show()

                })
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }



}