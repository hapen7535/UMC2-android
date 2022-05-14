package com.example.example1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.example1.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

   private var information = arrayListOf("수록곡","상세정보","영상")

    private var isLiked : Boolean = false

   lateinit var binding: FragmentAlbumBinding
   private var gson : Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumJson = arguments?.getString("album") //arguments에서 앨범 객체를 꺼낸다.
        val album = gson.fromJson(albumJson, Album::class.java) //꺼낸 데이터를 Json 형태로 변환한다. 그리고 다시 그 객체를 Album 객체로 변환한다.
        isLiked = isLikedAlbum(album.id)
        setInit(album) //setInit()를 통해 데이터를 bind해준다.
        setOnClickListeners(album)

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {//Tab이 선택될 때 ViewPager의 위치를 선택된 Tab의 위치와 동기화하고 사용자가 스크롤할 때 스크롤의 위치를 동기화해준다.
            tab, position ->
            tab.text = information[position]
        }.attach() //Tab과 ViewPager를 붙여주는 역할을 해주는 attach()
        return binding.root
    }

    private fun setInit(album : Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        if(isLiked){
            binding.albumAlbumIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumAlbumIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt():Int{ //현재 id 가져오기
        val spf =activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE) //Fragment에서는 AppCompatActivity.?로 적음
        return spf!!.getInt("jwt", 0) //없으면 0을 반환
    }
    private fun likeAlbum(userId : Int, albumId : Int){ //좋아요 버튼 누르면 DB에 추가
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)

    }

    private fun isLikedAlbum(albumId: Int):Boolean{ //유저가 좋아요를 눌렀는지?
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId = songDB.albumDao().isLikedAlbum(userId, albumId)
        return likeId != null
    }
    private fun disLikedAlbum(albumId: Int){ //싫어요 눌렀으면 지움
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun setOnClickListeners(album : Album){ //좋아요 버튼 눌렀을 때 뷰 초기화
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener{
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }
}