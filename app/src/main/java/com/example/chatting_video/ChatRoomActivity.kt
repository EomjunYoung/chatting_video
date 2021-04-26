package com.example.chatting_video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chatting_video.Model.ChatLeftYou
import com.example.chatting_video.Model.ChatRightMe
import com.example.chatting_video.databinding.ActivityChatListBinding
import com.example.chatting_video.databinding.ActivityChatRoomBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatRoomActivity : AppCompatActivity() {

    private var _binding: ActivityChatRoomBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatLeftYou())
        adapter.add(ChatLeftYou())
        adapter.add(ChatLeftYou())
        adapter.add(ChatRightMe())
        adapter.add(ChatRightMe())
        adapter.add(ChatRightMe())


        binding.recyclerViewChat.adapter = adapter

    }

}