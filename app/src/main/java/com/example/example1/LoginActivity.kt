package com.example.example1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.example1.databinding.ActivityLoginBinding
import kotlin.Result

class LoginActivity : AppCompatActivity(), LoginView{
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginSignInBtn.setOnClickListener{
            login()
        }


    }

    private fun login(){
        //입력하지 않은 곳이 없는지 확인을 하는 validation 체크가 필요
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){ //이메일이 비어있을 경우
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPasswordEt.text.toString().isEmpty() ){ //비밀번호가 비어있을 경우
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }


        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(email,pwd,"")
//
//        //DB에 사용자가 입력한 정보가 존재하는지 확인
//        val songDB = SongDatabase.getInstance(this)!! //songDB 연결
//        val user = songDB.UserDao().getUser(email,pwd)
//
//        //DB에 사용자의 정보가 있는 경우
//        user?.let{
//            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, $user")
//            saveJwt(user.id)
//        }
//
//        //사용자가 입력한 정보가 DB에 없다면
//        Toast.makeText(this, "회원정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()

    }

//    private fun saveJwt(jwt:Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt",jwt) //키 값은 받은 jwt 값이 된다.
//        editor.apply()
//    }

    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code : Int , result: Result) {
        when(code) {
            1000 -> {
                saveJwt2(result.jwt)
                startMainActivity()

            }
        }
    }

    override fun onLoginFailure() {
        TODO("Not yet implemented")
    }
}