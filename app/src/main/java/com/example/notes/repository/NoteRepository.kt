package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.RoomDatabase.Note
import com.example.notes.RoomDatabase.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

  /*  suspend fun delete(note: Note){
        noteDao.delete(note)
    } */

    suspend fun update(note: Note){
        noteDao.update(note)
    }

    fun deletebyId(note: Note){
        noteDao.deletebyId(note.id)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }

}