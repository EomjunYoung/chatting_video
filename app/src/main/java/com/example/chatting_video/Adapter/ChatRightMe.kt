package com.example.chatting_video.Adapter

import android.widget.TextView
import com.example.chatting_video.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatRightMe(val msg: String) : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {

        return R.layout.chat_right_me
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        val name = viewHolder.itemView.findViewById<TextView>(R.id.right_msg)
        name.text = msg
    }

}