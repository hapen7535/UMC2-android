package com.example.example1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.example1.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            finish()
        }
    }
    private fun getUser() : User{
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()

        return User(email, pwd)
    }

    private fun signUp(){
        //validation 처리
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){ //아이디와 직접 입력란이 비어있을 경우
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString() ){
            Toast.makeText(this, "패스워드가 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        //validation이 되었다면 회원가입이 정상적으로 진행되므로  UserTable에 저장
        val userDB = SongDatabase.getInstance(this)!!
        userDB.UserDao().insert(getUser()) //getUser는 지금 입력된 사용자의 정보를 말한다.

        //log로 DB에 저장이 되었는지 확인
        val user = userDB.UserDao().getUsers()
        Log.d("SIGNUPACT", user.toString())

    }

}