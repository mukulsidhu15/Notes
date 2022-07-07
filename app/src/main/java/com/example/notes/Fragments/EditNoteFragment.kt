package com.example.notes.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.RoomDatabase.Note
import com.example.notes.ViewModels.NoteViewModel
import com.example.notes.databinding.FragmentEditNoteBinding
import com.example.notes.databinding.FragmentNotesBinding



class EditNoteFragment : Fragment() {
    lateinit var binding: FragmentEditNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val titleNote = requireArguments().getString("title")
        val description = requireArguments().getString("des")
        val adapterPos: Int = requireArguments().getInt("position")

        position = adapterPos
        binding.editTitle.setText(titleNote)
        binding.editDes.setText(description)

        binding.btnSave.setOnClickListener(){
            UpdateNote()
            onDestroy()
            activity?.supportFragmentManager?.popBackStack()
        }




        return binding.root
    }

    private fun UpdateNote(){
        val noteTitle= binding.editTitle.text.toString()
        val noteDescription = binding.editDes.text.toString()

        if (inputCheck(noteTitle,noteTitle)){
            val updateNote = Note(position,noteTitle, noteDescription)
           // noteViewModel.addNotes(note)
            noteViewModel.update(updateNote)
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean{
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }




}