package com.example.chatting_video.Model

import com.example.chatting_video.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserItem : Item<GroupieViewHolder>(){
    override fun getLayout(): Int {

        return R.layout.message_list_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

}