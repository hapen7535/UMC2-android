package com.example.example1

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.example1.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList : ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
       fun onItemClick(album : Album)//외부에서 클릭 이벤트를 사용하기 위해, 외부에서 리스너 객체를 넘겨주어야 한다.
            //따라서 외부에서 전달받는 함수랑 외부에서 전달받는 리스너 객체를 어댑터에서 사용할 수 있도록 따로 저장할 변수를 선언해줘야 한다.

    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListner(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //ViewHolder를 생성해줘야 할 때 호출되는 함수이다.
        //이곳에서 아이템 뷰 객체를 재활용하기 위해 ViewHolder에 던져줘야 한다.
        val binding : ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        //위 binding 코드가 왜 저렇게 되는지는 따로 구글링 필요
        //중요한 것은 사용하고자 하는 아이템 뷰를 만들어줘야 하고 그 아이템 뷰를 뷰홀더에 던져줘야 한다는 것이다.
        //그래서 return으로 ViewHolder에게 아이템 뷰 객체를 던져준다.
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        //바인딩할 때마다 호출된다 따라서 사용자가 스크롤할 때마다 호출되는 함수다.
        //반면에 onCreateViewHolder의 경우, 리사이클러뷰는 처음에 화면에 몇 개 정도의 아이템을 생성하고 이를 계속 재활용하므로 처음에 생성될 때 몇 번 호출되고 말 것이다.
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(albumList[position])
        }
    }

    override fun getItemCount(): Int = albumList.size //데이터 셋의 크기를 알려주는 함수, 리사이클러뷰의 마지막이 언제인지 알려주는 함수

    inner class ViewHolder(val binding : ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(album : Album){
                binding.itemAlbumTitleTv.text = album.title
                binding.itemAlbumSingerTv.text = album.singer
                binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
            }
    }



}