package com.example.notes.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String,
    val description: String)
