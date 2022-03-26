package com.example.example1

import android.media.AsyncPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity //안드로이드에서 Activity의 기능들을 사용할 수 있도록 만들어둔 클래스가 AppCompatActivity이다
import com.example.example1.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity()  { //코틀린에서는 extends대신에 : 콜론으로 상속을 받는다 다른 클래스의 상속을 받을 때는 소괄호를 써준다(코틀린에서는)

    lateinit var binding : ActivitySongBinding//lateinit을 이용해서 나중에 초기화를 해준다는 것으로 표시해둔다
    //Binding은 Activity파일과 xml파일을 연결해주는 역할

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate가 AppCompat안에 있으므로 override를 써준다
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater) //inflate는 xml에 표기된 레이아웃들을 메모리에 객체화시키는 역할
        setContentView(binding.root) //xml에 있는 모든 객체들을 사용한다고 명시 xml의 최상단 뷰를 넣어주기 때문

        binding.songDownIb.setOnClickListener{

            finish() //액티비티 종료 처음에 연 액티비티로 돌아감

        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }


    }

    fun setPlayerStatus(isPlaying: Boolean){

        if(isPlaying){
            binding.songPauseIv.visibility = View.GONE
            binding.songMiniplayerIv.visibility = View.VISIBLE
        }
        else{
            binding.songPauseIv.visibility = View.VISIBLE
            binding.songMiniplayerIv.visibility = View.GONE
        }
    }

}