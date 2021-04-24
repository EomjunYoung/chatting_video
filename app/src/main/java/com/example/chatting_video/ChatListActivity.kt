package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_video.databinding.ActivityChatListBinding
import com.example.chatting_video.databinding.ActivityLoginBinding
import com.example.chatting_video.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class ChatListActivity : AppCompatActivity() {

    private var _binding: ActivityChatListBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = ChatListActivity::class.java.simpleName

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        }
    }

