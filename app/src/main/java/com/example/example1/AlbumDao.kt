package com.example.example1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface AlbumDao {

    @Insert
    fun insert(album : Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums():List<Album>

    @Insert
    fun likeAlbum(like : Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId") //현재 사용자를 뜻하는 userId와 화면에 들어간 앨범을 뜻하는 albumId를
    //LikeTable에 있는지 확인해서 있으면 LikeTable의 id 반환
    fun isLikedAlbum(userId:Int, albumId:Int)

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int)

    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId") //보관함에서 좋아요한 앨범을 가져오는 함수
    fun getLikedAlbum(userId:Int) : List<Album>
}