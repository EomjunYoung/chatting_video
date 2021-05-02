package com.example.chatting_video

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chatting_video.Adapter.ChatLeftYou
import com.example.chatting_video.Adapter.ChatRightMe
import com.example.chatting_video.Model.ChatModel
import com.example.chatting_video.Model.ChatNewModel
import com.example.chatting_video.databinding.ActivityChatRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
        var myUid = auth.uid //로그인한 사람의 Uid

        val adapter = GroupAdapter<GroupieViewHolder>()

//        adapter.add(ChatLeftYou())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatLeftYou())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatRightMe())
//        adapter.add(ChatRightMe())





        /**
         *
         * firebase database에서 message라는 컬렉션을 불러와 로그확인하는 작업
         *
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("message")
                .orderBy("time")//메시지를 시간의 순서대로 받아오겠다
                .get()
                .addOnSuccessListener { result ->
                    for(document in result){
                        Log.d(TAG, document.toString())

                        val senderUid = document.get("yourUid")//메시지 보낸사람의 Uid
                        val msg = document.get("message").toString()

                        //같으면 동일인이므로 오른쪽에 표시돼야함
                        if(senderUid == myUid){

                            adapter.add(ChatRightMe(msg))
                        }else{

                            adapter.add(ChatLeftYou(msg))
                        }

                    }

                    binding.recyclerViewChat.adapter = adapter
                }

         **/
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        //나를 기준으로 대화하고있는 상대방의 정보를 가지고오겠다.
        val readRef = database.getReference("message").child(myUid.toString()).child(yourUid.toString())

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            //메시지 받아오는 콜백메소드
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "snapshot: " + snapshot)

                val model = snapshot.getValue(ChatNewModel::class.java)
//                Log.d(TAG, "msg: " + msg)

                val msg: String? = model?.message
                val who: String? = model?.who

                //내가보낸메시지면
                if(who.equals("me")){

                    adapter.add(ChatRightMe(msg.toString()))
                }else{

                    adapter.add(ChatLeftYou(msg.toString()))
                }


            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }


        }
        binding.recyclerViewChat.adapter = adapter 
        readRef.addChildEventListener(childEventListener)
        binding.button.setOnClickListener {

            val message: String = binding.editText.text.toString()

            //메시지를 보낸사람
            val chat = ChatNewModel(myUid.toString(), yourUid.toString(), message, System.currentTimeMillis(), "me")
            myRef.child(myUid.toString()).child(yourUid.toString()).push().setValue(chat)

            //메시지를 받은사람
            val chat_get = ChatNewModel(yourUid.toString(), myUid.toString(), message, System.currentTimeMillis(), "you")
            myRef.child(yourUid.toString()).child(myUid.toString()).push().setValue(chat_get)

            binding.editText.setText("")// 메시지를 보낸다음에는 초기화

        /**
         *
         * firestore를 이용한 단순 메시지 보내기 읽기 코드
         *
            val message: String = binding.editText.text.toString()
            binding.editText.setText("")// 메시지를 보낸다음에는 초기화

            val chat = ChatModel(myUid.toString(), yourUid.toString(), message, System.currentTimeMillis())

            //db.collection.add는 Firebase에 db를 삽입하는 것
            db.collection("message")
                    .add(chat)
                    .addOnSuccessListener{

                        Log.d(TAG, "성공")
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "실패")
                    }
         **/




        }

    }

}