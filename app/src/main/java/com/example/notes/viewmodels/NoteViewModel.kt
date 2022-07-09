package com.example.notes.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.repository.NoteRepository
import com.example.notes.RoomDatabase.Note
import com.example.notes.RoomDatabase.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    val titleList = ArrayList<String>()

    private val repository: NoteRepository
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
         repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun addNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
                  repository.insert(note)
        }
    }

    fun update(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note)
        }
    }

    fun deletebyId(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletebyId(note)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    fun get(note: Note){
       titleList.add(note.title)
    }

}