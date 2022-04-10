package com.example.example1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.example1.databinding.ActivityMainBinding
import com.example.example1.databinding.FragmentAlbumBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false)

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)
        }




    }


    private fun initBottomNavigation(){
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