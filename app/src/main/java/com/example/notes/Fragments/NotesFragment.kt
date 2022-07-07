package com.example.notes.Fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Adapter.ListAdapter
import com.example.notes.R
import com.example.notes.ViewModels.NoteViewModel
import com.example.notes.databinding.FragmentNotesBinding


class NotesFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var binding: FragmentNotesBinding
    private lateinit var noteViewModel: NoteViewModel
    lateinit var adapter : ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesBinding.inflate(inflater, container, false)

        //toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.show()

        //Recycler View
        adapter= ListAdapter(requireContext())
        val recyclerView = binding.recyclerviewNotes
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserView Model
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { note->
            adapter.setData(note)
        })


        binding.addingBtn.setOnClickListener(){
            activity?.supportFragmentManager?.commit {

              /*  setCustomAnimations(
                    R.animator.
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )  */
                setReorderingAllowed(true)
                hide(activity?.supportFragmentManager?.findFragmentByTag("main_frag")!!)
                add(R.id.fragmentContainer,AddNoteFragment())
                addToBackStack(null)
            }

        }
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.search, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
       if (newText != null){
           searchDataBase(newText)
       }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.fav ->{
                Toast.makeText(context,"fav", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchDataBase(query: String){
        val searchQuery = "%$query%"
        noteViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer { note->
            note.let {
                adapter.setData(it)
            }
        })
    }




}