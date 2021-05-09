package com.example.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.chatting_video.Model.User
import com.example.chatting_video.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//test
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //test test
        auth = FirebaseAuth.getInstance()

        binding.loginButtonMain.setOnClickListener{

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

//            GlobalScope.launch(){
//                repeat(10){
//
//                    delay(1000L)
//                    Log.d("eom", "됨?")
//                }
//            }
//
//            Log.d("eom", "됨2?")

            /**
             *
            UI갱신이나 Toast등의 View작업시 Main,
            네트워킹이나 내부DB접근시 IO
            크기가 큰 리스트를 다루거나 필터링 등 무거운 연산을 다룰때 Default

            이 코드는 getResultFromApi()까지는 좋았으나 textView는 UI 갱신이므로 Main으로 작업
            CoroutineScope(IO).launch{
                val resultStr = getResultFromApi()
                textView.text = resultStr
            }

            위 코드를 이런식으로 작업을 진행해줘야 한다
            CoroutineScope(IO).launch{
            val resultStr = getResultFromApi()

            CoroutineScope(Main).launch{
            textView.text = resultStr
                }
            }

             하지만 위와같은 방식은 Coroutine을 또 생성해야하므로 리소스낭비가 있고, 가독성도 떨어진다
             그래서 나온것이 withContext

            CoroutineScope(IO).launch {
            val resultStr = getResultFromApi() //resultStr = "ok"

            withContext(Main) {
            textView.text = resultStr
                }
            }

             코루틴이 Thread로부터 독립적이기때문에 Main Thread에서 작업중인걸 다른곳으로 보내서
             작업하는 방식이 가능한데, 컨텍스트 스위칭 작업을 해주는 것이 withContext다

             
            **/

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