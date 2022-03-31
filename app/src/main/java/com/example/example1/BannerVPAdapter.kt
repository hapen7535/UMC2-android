package com.example.example1

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    //인자 값으로 fragment를 주는 이유는 ViewPager 안에 Image가 들어가는 것이 아니라 fragment를 각각 넣어줘야 하기 때문이다.
    //FragmentStateAdapter는 원래부터 fragment를 하나의 인자 값으로 갖는다.
    //상속받은 클래스가 실행될 때 꼭 필요한 함수라서 멤버 함수를 선언해줘야 한다.

    private val fragmentlist : ArrayList<Fragment> = ArrayList() //ViewPager에 들어갈 fragment 여러 개를 담아둘 공간
    //private인 이유는 해당 클래스 내에서만 사용될 것이기 때문에 private으로 설정한 것이다.
    //private이라고 써주지 않으면 HomeFragment에서 사용이 될 것이다. 아래와 같이
    //BannerVPAdapter(this).fragmentlist를 HomeFragment.kt에서 사용될 수 있다.
    //위와 같이 외부에서도 데이터의 변경이 일어날 수 있으므로 private을 써놓는 것이다.

    /*
    override fun getItemCount(): Int { //이 클래스에서 연결된 ViewPager에게 데이터를 몇 개를 전달할 것인지 정의
        //fragmentlist에 담긴 데이터의 개수가 들어간다.
        return fragmentlist.size
    }*/
    //위와 같이 함수를 써줄 수도 있지만 아래와 같이 함수를 쓰면 더 간결해진다.

    override fun getItemCount() : Int = fragmentlist.size

    /*
    override fun createFragment(position: Int): Fragment { //fragmentlist안에 있는 아이템들, fragment들을 생성해주는 함수
        return fragmentlist[position]
    }
    //이 함수 또한 아래와 같이 간단하게 쓴다.
     */

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment) { //이 함수가 처음 실행될 때, fragmentlist에는 아무것도 없는데,
        //HomeFragment에서 fragment를 추가해주기 위해서 필요한 함수이다.
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size - 1) //새로운 값이 리스트에 추가되는 것을 정의
        //처음에 리스트에 들어갈 때는 0부터 들어가므로 -1을 해준다.
    }

}