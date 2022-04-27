package com.example.example1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.example.example1.databinding.ActivityMainBinding
import com.example.example1.databinding.FragmentAlbumBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var song: Song = Song()
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Example1)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Thread 이용해서 이미지 바꾸는 실습
        //메인 Thread는 이미지 바꾸는 일을 계속함
        //그렇기때문에 다른 버튼을 누르는 것과 같은 다른 일들을 수행할 수 없음
        //다른 버튼을 누르면 앱이 중지될 수 있다.

        /*
         아래와 같이 실행시키면 오류가 난다.
         Only the original Thread that created a view
         메인 스레드만 뷰 렌더링을 할 수 있기 때문이다.
         workThread에서 실행하려고 했기 때문에 오류가 난 것이다.
         1)handler를 이용해서 해결가능 handler란 Thread 간 통신을 도와주는 클래스이다.
         혹은 2)상속받은 액티비티에서 제공해주는 함수를 사용하는 방법이 있다.



        val handler = Handler(Looper.getMainLooper()) //메시지 처리를 위해서 메시지 루퍼를 실행해서 다른 스레드에서 전달한 메시지들을 전달해주는 역
        //메인 스레드의 고유한 루퍼를 가져와서 핸들러에 전달시켜줌
        val imageList = ArrayList<Int>()

        imageList.add(R.drawable.apple_44)
        imageList.add(R.drawable.kakako_44)
        imageList.add(R.drawable.naver_44)
        imageList.add(R.drawable.apple_44)
        imageList.add(R.drawable.kakako_44)
        imageList.add(R.drawable.naver_44)
        imageList.add(R.drawable.apple_44)
        imageList.add(R.drawable.kakako_44)
        imageList.add(R.drawable.naver_44)

        Thread{
            for(image in imageList){
               runOnUiThread{
                   //이 내부의 코드는 메인 스레드에서 실행되게 된다.
                   binding.iv.setImageResource(image)
               }
               /*
               1) handler.post{
                    //여기 코드는 메인 스레드로 전달되어서 뷰 렌더링 하는 작업이 메인 스레드에서 이뤄져서 오류 없이 실행된다.
                    binding.iv.setImageResource(image)
                }*/
                Thread.sleep(2000) //2초에 한 번씩
            }
        }.start()
        */


        initBottomNavigation()

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false, "music_lilac")

        binding.mainMiniplayerTitleTv.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music",song.music)
            startActivity(intent)
        }

        binding.mainMiniplayerBtn.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.mainPauseBtn.setOnClickListener{
            setPlayerStatus(false)
        }

    }

    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000)/song.playTime
    }


    override fun onStart(){ //액티비티 전환이 될 때 onStart부터 시작이 되므로 만들어준다.
        //onStart()가 사용자에게 보여지기 직전의 함수이고 onResume()은 사용자에게 보여지고 난 후의 함수이므로
        // onStart()에서 UI와 관련된 코드를 초기화해주는 것이 더 안정적이다.
        super.onStart()
        val sharedPreference = getSharedPreferences("song", MODE_PRIVATE) //sharedPreference의 이름
        val songJson = sharedPreference.getString("songData", null) //sharedPreference 안에 저장된 데이터의 이름

        //가져온 데이터를 저장한다.
        song = if(songJson == null){
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        } else{
            gson.fromJson(songJson, Song::class.java) //songJson을 Song 클래스에 자바 객체로 변환해주라 요청
        }

        setMiniPlayer(song)

    }

    fun setPlayerStatus(isPlaying: Boolean){

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        }
        else{
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility= View.GONE
        }

    }


    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitNowAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}