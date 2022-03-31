package com.example.example1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.example1.databinding.FragmentDetailBinding
import com.example.example1.databinding.FragmentVideoBinding

class VideoFragment : Fragment(){

    lateinit var binding : FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

}