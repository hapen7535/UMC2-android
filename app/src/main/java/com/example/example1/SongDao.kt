package com.example.example1

import androidx.room.*

@Dao
interface SongDao {
    @Insert
    fun insert(song : Song)

    @Delete
    fun delete(song: Song)

    @Update
    fun update(song: Song)

    @Query("SELECT * FROM SongTable")
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id")
    fun getSongs(id : Int): Song
}