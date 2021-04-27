package com.example.chatting_video.Adapter

import android.widget.TextView
import com.example.chatting_video.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserItem(val name: String, val uid: String) : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {

        return R.layout.message_list_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

       val name = viewHolder.itemView.findViewById<TextView>(R.id.name)
        name.text = this.name


    }

}