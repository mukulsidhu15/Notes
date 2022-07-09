package com.example.notes.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.RoomDatabase.Note
import com.example.notes.viewmodels.NoteViewModel
import com.example.notes.databinding.FragmentAddNoteBinding



class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        val autoSuggestion: ArrayList<String> = ArrayList()

        // use viewmodel for autoSuggestion when user type in title field
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { note->
            val noteTitle: ArrayList<Note> = note as ArrayList<Note>
            for(i in noteTitle){
                autoSuggestion.add(i.title)
            }
            val suggestionList = autoSuggestion.distinct()
            arrayAdapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, suggestionList.takeLast(5))
            binding.editTextTitle.setAdapter(arrayAdapter)

        })


        binding.saveNotesButton.setOnClickListener(){
            insertNoteToDataBase()
            onDestroy()
            activity?.supportFragmentManager?.popBackStack()
        }

        return binding.root

    }

    // add new note to Database
    private fun insertNoteToDataBase(){
        val noteTitle= binding.editTextTitle.text.toString()
        val noteDescription = binding.editTextDescriptiton.text.toString()

        if (inputCheck(noteTitle,noteTitle)){
            val note = Note(0,noteTitle, noteDescription)
            noteViewModel.addNotes(note)
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Not Added", Toast.LENGTH_SHORT).show()
        }
    }

    //check title and description empty or not
    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean{
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }


}