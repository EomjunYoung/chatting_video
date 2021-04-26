package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_video.Model.UserItem
import com.example.chatting_video.databinding.ActivityChatListBinding
import com.example.chatting_video.databinding.ActivityLoginBinding
import com.example.chatting_video.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatListActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private var _binding: ActivityChatListBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = ChatListActivity::class.java.simpleName

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerviewList.adapter = adapter

        db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        Log.d(TAG, "${document.get("username").toString()}")

                    adapter.add(UserItem(document.get("username").toString()))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }

        adapter.setOnItemClickListener { item, view ->

            val intent = Intent(this, ChatRoomActivity::class.java)
            startActivity(intent)
        }

    }

}

