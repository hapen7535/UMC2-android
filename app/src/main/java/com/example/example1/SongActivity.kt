package com.example.example1

import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity //안드로이드에서 Activity의 기능들을 사용할 수 있도록 만들어둔 클래스가 AppCompatActivity이다
import com.example.example1.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity()  { //코틀린에서는 extends대신에 : 콜론으로 상속을 받는다 다른 클래스의 상속을 받을 때는 소괄호를 써준다(코틀린에서는)


    lateinit var binding : ActivitySongBinding//lateinit을 이용해서 나중에 초기화를 해준다는 것으로 표시해둔다
    //Binding은 Activity파일과 xml파일을 연결해주는 역할
    lateinit var timer : Timer
    private var mediaPlayer : MediaPlayer?= null //액티비티가 소멸될 때 미디어 플레이어 리소스를 해제시켜줘야 하므로 nullable ? 사용
    private var gson : Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB : SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate가 AppCompat안에 있으므로 override를 써준다
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater) //inflate는 xml에 표기된 레이아웃들을 메모리에 객체화시키는 역할
        setContentView(binding.root) //xml에 있는 모든 객체들을 사용한다고 명시 xml의 최상단 뷰를 넣어주기 때문

        initPlayList()
        initSong()
        initClickListener()

    }
    //사용자가 포커스를 잃었을 때 음악 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000 //millisecond로 계산되므로 1000으로 나눠줌
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //중지되면 재생되고 있던 노래의 데이터를 어딘가 저장해주기 위함
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)

        editor.apply() //실제 저장이 된다.
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //불필요한 리소스 해제를 위해 미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 또한 해제
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initSong(){
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString()) //확인용
        startTimer()

        setPlayer(songs[nowPos])
    }

    private fun initClickListener(){

        binding.songDownIb.setOnClickListener{

            finish() //액티비티 종료 처음에 연 액티비티로 돌아감

        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener{
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }

    }

    private fun setLike(isLike : Boolean){ //isLike는 현재 눌려있는지 아닌지 확인용
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike, songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

    }


    private fun moveSong(direct : Int){
        if(nowPos + direct < 0){ //이게 처음 노래임
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){ //다음 노래 없음
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct //다음 노래로 넘겨준다.

        timer.interrupt() //타이머를 멈추고
        startTimer() //재시작

        mediaPlayer?.release() //리소스 해제
        mediaPlayer = null

        setPlayer(songs[nowPos]) //데이터 렌더링
    }

    private fun getPlayingSongPosition(songId : Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying: Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPauseIv.visibility = View.VISIBLE
            binding.songMiniplayerIv.visibility = View.GONE
            mediaPlayer?.start()
        }
        else{
            binding.songPauseIv.visibility = View.GONE
            binding.songMiniplayerIv.visibility = View.VISIBLE
            if(mediaPlayer?.isPlaying == true){ //마디어플레이어는 재생중이 아닌데 중지하게 되면 오류가 생기므로 예외처리를 해준다.
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime,songs[nowPos].isPlaying)
        timer.start()
    }



    inner class Timer(private val playTime : Int, var isPlaying: Boolean = true):Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run(){
            super.run()
            try{
                while(true){
                    if(second >= playTime){
                        break
                    }
                    if(isPlaying){
                        sleep(50) //50mills 단위로 관리
                        mills += 50
                        runOnUiThread{
                            //seekBar가 계속 움직어야 하므로 스레드로 움직여줌
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread{
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e:InterruptedException){
                Log.d("song", "Thread가 죽었습니다. ${e.message}")
            }

        }
    }

}