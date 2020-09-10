package com.napoleon.ui.adapters



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.napoleon.R
import com.napoleon.data.model.PostSqlite
import com.napoleon.databinding.ItempostBinding


class PostAdapter() : ListAdapter<PostSqlite, PostAdapter.ViewHolder>(PostDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)

        val items = position

        holder.bind(item, items)
    }

    class ViewHolder private constructor(private var binding: ItempostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // receives parameters possquile an position of item
        fun bind(item: PostSqlite, position: Int) {

            //init binding to item
            binding.postsqlite = item
            binding.executePendingBindings()

            //init the functions
            setColorunReaded(position)
            setStartoFavorites(item)
            setColorReaded(item)

            // listenes when the item is clicked
            itemView.setOnClickListener {

                //declared and init bundle to send to the next details views
                val bundle = Bundle()
                bundle.putParcelable("post", item)

                //navigate to details view
                it.findNavController().navigate(
                    R.id.action_allPostFragment_to_detailsPostFragment,
                    bundle
                )
            }
        }

        //set the color of title to black
        fun setColortitle(){
            binding.titlePost.setTextColor(
                ActivityCompat.getColor(
                    itemView.context,
                    android.R.color.black
                )
            )
        }

        //set the start image when item is favorite and set the color of title
        fun setStartoFavorites(item: PostSqlite){
            if (item.statePost == "favorites"){

                //start is visible
                binding.favorites.visibility = View.VISIBLE
                setColortitle()

            }else{
                //start is invisible
                binding.favorites.visibility = View.INVISIBLE
            }
        }

        //set the color of title to blue when the post is unreaded
        // is blue to the first 20 items
        fun setColorunReaded(position: Int){
            if (position < 20){
                binding.titlePost.setTextColor(
                    ActivityCompat.getColor(
                        itemView.context,
                        R.color.colorAccent
                    )
                )
            }else{
                binding.titlePost.setTextColor(
                    ActivityCompat.getColor(
                        itemView.context,
                        android.R.color.black
                    )
                )
            }
        }

        // set the color when the status of post is readed
        fun setColorReaded(item: PostSqlite){
            if (item.statePost == "readed"){
                setColortitle()
            }
        }

        //User to inflate the view
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItempostBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}

// class to update items when the data change
class PostDiffCallback : DiffUtil.ItemCallback<PostSqlite>() {
    override fun areItemsTheSame(oldItem: PostSqlite, newItem: PostSqlite): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: PostSqlite, newItem: PostSqlite): Boolean {
        return oldItem == newItem
    }

}