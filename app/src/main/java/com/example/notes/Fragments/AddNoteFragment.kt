package com.example.notes.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.notes.R
import com.example.notes.RoomDatabase.Note
import com.example.notes.ViewModels.NoteViewModel
import com.example.notes.databinding.FragmentAddNoteBinding
import com.example.notes.databinding.FragmentNotesBinding

class AddNoteFragment : Fragment() {

    lateinit var binding: FragmentAddNoteBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)


        binding.saveNotesButton.setOnClickListener(){
            insertNoteToDataBase()
            onDestroy()
            activity?.supportFragmentManager?.popBackStack()
        }

        return binding.root

    }

    private fun insertNoteToDataBase(){
        val noteTitle= binding.editTextTitle.text.toString()
        val noteDescription = binding.editTextDescriptiton.text.toString()

        if (inputCheck(noteTitle,noteTitle)){
            val note = Note(0,noteTitle, noteDescription)
            noteViewModel.addNotes(note)
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean{
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }


}