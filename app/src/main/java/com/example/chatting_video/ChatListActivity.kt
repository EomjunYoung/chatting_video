package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_video.Adapter.UserItem
import com.example.chatting_video.databinding.ActivityChatListBinding
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

        binding.myChatList.setOnClickListener{
            val intent = Intent(this, MyRoomActivity::class.java)
            startActivity(intent)
        }

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerviewList.adapter = adapter

        //db.collection.get은 Firebase에서 db를 가지고 오는 것
        db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                        Log.d(TAG, "${document.get("username").toString()}")

                    adapter.add(UserItem(document.get("username").toString(), document.get("uid").toString()))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }

        adapter.setOnItemClickListener { item, view ->

//            Log.d(TAG, (item as UserItem).name)
//            Log.d(TAG, (item as UserItem).uid)

            val uid: String = (item as UserItem).uid
            val name: String = (item as UserItem).name

            val intent = Intent(this, ChatRoomActivity::class.java)

            intent.putExtra("yourUid", uid)
            intent.putExtra("name", name)
            startActivity(intent)
        }

    }

}

