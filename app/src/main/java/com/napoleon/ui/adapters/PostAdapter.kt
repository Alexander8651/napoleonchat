package com.napoleon.ui.adapters



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
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

        fun bind(item: PostSqlite, position: Int) {

            binding.postsqlite = item
            binding.executePendingBindings()

            setColorunReaded(position)
            setStartoFavorites(item)
            setColorReaded(item)

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("post", item)

                it.findNavController().navigate(R.id.action_allPostFragment_to_detailsPostFragment, bundle)
            }
        }

        fun setColortitle(){
            binding.titlePost.setTextColor(
                ActivityCompat.getColor(
                    itemView.context,
                    android.R.color.black
                ))
        }

        fun setStartoFavorites(item:PostSqlite){
            if (item.statePost == "favorites"){
                binding.favorites.visibility = View.VISIBLE
                setColortitle()

            }else{
                binding.favorites.visibility = View.INVISIBLE
            }
        }

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
                    ))
            }
        }

        fun setColorReaded(item: PostSqlite){
            if (item.statePost == "readed"){
                setColortitle()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItempostBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}

class PostDiffCallback : DiffUtil.ItemCallback<PostSqlite>() {
    override fun areItemsTheSame(oldItem: PostSqlite, newItem: PostSqlite): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: PostSqlite, newItem: PostSqlite): Boolean {
        return oldItem == newItem
    }

}