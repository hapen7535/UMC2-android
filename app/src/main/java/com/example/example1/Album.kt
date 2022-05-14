package com.example.example1

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id : Int = 0,
    var title: String ?= "",
    var singer : String ?= "",
    var coverImg : Int ?= null,
    //var songs : ArrayList<Song> ?= null //앨범의 수록곡을 넣기 위함 //지금은 필요 없음


)
