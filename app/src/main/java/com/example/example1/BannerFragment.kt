package com.example.example1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.example1.databinding.FragmentBannerBinding

class BannerFragment(val imgRes : Int) : Fragment() { //ViewPager에 여러 개의 이미지를 넣을 것이므로 BannerFragment가 추가할 때마다 새로운 이미지를 넣을 수 있도록
    //이미지를 인자 값으로 받아온다.

    lateinit var binding : FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBannerBinding.inflate(inflater, container, false)

        binding.bannerImageIv.setImageResource(imgRes) //배너에 인자 값을 줘서 img의 src를 변경하도록 한다.

        return binding.root
    }

}