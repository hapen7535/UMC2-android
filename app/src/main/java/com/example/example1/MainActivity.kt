package com.example.example1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.example1.databinding.ActivityMainBinding
import com.example.example1.databinding.FragmentAlbumBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        binding.homePannelPlayIv.setOnClickListener{

            startActivity(Intent(this, SongActivity::class.java))

        }
        binding.homePannelAlbumIv.setOnClickListener{
            Toast.makeText(this.applicationContext,"album 클릭", Toast.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment())
                .commitNowAllowingStateLoss()
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