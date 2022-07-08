package com.example.notes.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notes.RoomDatabase.Note
import com.example.notes.viewmodels.NoteViewModel
import com.example.notes.databinding.FragmentEditNoteBinding


class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val noteTitle = requireArguments().getString("title")
        val noteDescription = requireArguments().getString("des")
        val notePosition: Int = requireArguments().getInt("position")

        position = notePosition
        binding.editTitle.setText(noteTitle)
        binding.editDes.setText(noteDescription)

        binding.btnSave.setOnClickListener(){
            updateNote()
            onDestroy()
            activity?.supportFragmentManager?.popBackStack()
        }

        return binding.root
    }

    //for update or edit the note
    private fun updateNote(){
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

    //check title and description empty or not
    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean{
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }


}