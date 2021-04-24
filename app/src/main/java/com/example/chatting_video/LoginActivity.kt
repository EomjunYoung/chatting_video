package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chatting_video.databinding.ActivityLoginBinding
import com.example.chatting_video.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = LoginActivity::class.java.simpleName

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPwd.text.toString().trim()

            // Validate...
            loginUser(email, password)

        }
    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {

            if (it.isSuccessful) {
                Log.d(TAG, "로그인에 성공하였습니다!")

                var intent = Intent(this, ChatListActivity::class.java)
                //새화면으로 이동하면 전 액티비티를 없애기 위해
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            } else {

                Log.d(TAG, "로그인에 실패하였습니다!")
            }
        }
    }

}