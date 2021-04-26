package com.example.chatting_video.Model

import android.widget.TextView
import com.example.chatting_video.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ChatLeftYou() : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {

        return R.layout.chat_left_you
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

}