package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_video.Model.User
import com.example.chatting_video.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //test
        auth = FirebaseAuth.getInstance()

        binding.loginButtonMain.setOnClickListener{

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.joinButton.setOnClickListener{
            val email = binding.emailArea.text.toString()
            var password = binding.passwordarea.text.toString()

            auth.createUserWithEmailAndPassword(email, password )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        var uid = FirebaseAuth.getInstance().uid?:""
                        // Access a Cloud Firestore instance from your Activity
                        val db = Firebase.firestore.collection("users")
                        db.document(uid).set(User(uid, binding.username.text.toString()))
                                .addOnCompleteListener{
                                    Log.d(TAG, "성공")
                                }
                                .addOnFailureListener{
                                    Log.d(TAG, "실패")
                                }

                        var intent = Intent(this, ChatListActivity::class.java)
                        //새화면으로 이동하면 전 액티비티를 없애기 위해
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {

                        Log.d(TAG, "실패")
                    }
                }
        }

    }
}