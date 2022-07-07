package com.example.notes.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("Select * from notes order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM notes WHERE Id = :id")
    fun deletebyId(id: Int?)

    @Query("SELECT * FROM notes WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): kotlinx.coroutines.flow.Flow<List<Note>>




}