package com.example.example1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.example1.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

   private var information = arrayListOf("수록곡","상세정보","영상")
   lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {//Tab이 선택될 때 ViewPager의 위치를 선택된 Tab의 위치와 동기화하고 사용자가 스크롤할 때 스크롤의 위치를 동기화해준다.
            tab, position ->
            tab.text = information[position]
        }.attach() //Tab과 ViewPager를 붙여주는 역할을 해주는 attach()
        return binding.root
    }
}