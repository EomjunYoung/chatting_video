package com.example.chatting_video

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chatting_video.Adapter.ChatLeftYou
import com.example.chatting_video.Adapter.ChatRightMe
import com.example.chatting_video.Model.ChatModel
import com.example.chatting_video.databinding.ActivityChatRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var _binding: ActivityChatRoomBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = ChatRoomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //메시지를 보낼때 나의 uid도 필요하므로 나의 uid를 가지고오는 방법
        auth = FirebaseAuth.getInstance()

        //ChatListActivity로부터 메시지를 보낼 상대의 uid와 name을 가지고온다
        val yourUid: String? = intent.getStringExtra("yourUid")
        val name: String? = intent.getStringExtra("name")
        var myUid = auth.uid

        val adapter = GroupAdapter<GroupieViewHolder>()

//        adapter.add(ChatLeftYou())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatRightMe())

        binding.recyclerViewChat.adapter = adapter

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        binding.button.setOnClickListener {
            val message: String = binding.editText.text.toString()
            binding.editText.setText("")// 메시지를 보낸다음에는 초기화

            val chat = ChatModel(myUid.toString(), yourUid.toString(), message)

            db.collection("message")
                    .add(chat)
                    .addOnSuccessListener{

                        Log.d(TAG, "성공")
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "실패")
                    }

        }

    }

}