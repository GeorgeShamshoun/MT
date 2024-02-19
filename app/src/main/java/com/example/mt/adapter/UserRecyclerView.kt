package com.example.mt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mt.R
import com.example.mt.model.entity.User

class UserRecyclerView : RecyclerView.Adapter<UserRecyclerView.UserViewHolder>() {
    var onListItemClick : OnListItemClick? = null
    var userlist :List<User> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList (userList : List<User>){
        this.userlist = userList
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var iv_UserImage : ImageView = itemView.findViewById(R.id.iv_Item_UserImage)
         var tv_UserName : TextView = itemView.findViewById(R.id.tv_Item_UserName)
         var tv_Message : TextView = itemView.findViewById(R.id.tv_Item_message)

         fun bind ( user : User){
             iv_UserImage.setImageResource(user.imageId)
             tv_UserName.text = user.name
             tv_Message.text = user.message
             itemView.setOnClickListener{
                 onListItemClick?.onItemClick(user)

             }

         }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_2,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user : User = userlist.get(position)
        holder.bind(user)
    }
}