package com.example.example1

data class Album(

    var title: String ?= "",
    var singer : String ?= "",
    var coverImg : Int ?= null,
    var songs : ArrayList<Song> ?= null //앨범의 수록곡을 넣기 위함

)
