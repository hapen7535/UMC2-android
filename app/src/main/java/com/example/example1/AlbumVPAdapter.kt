package com.example.example1

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3 //수록곡, 상세정보, 영상 부분을 fragment로 받을 것이기 때문에 3개를 받는다.

    override fun createFragment(position: Int): Fragment {
        return when (position) { //각각의 다른 fragment들을 받아와야 하므로 when을 사용해서 3개의 다른 fragment를 받아온다.
            0 -> SongFragment()
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}