package com.example.notes.adapter

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.fragments.EditNoteFragment
import com.example.notes.R
import com.example.notes.RoomDatabase.Note
import com.example.notes.viewmodels.NoteViewModel

class ListAdapter(val context: Context): RecyclerView.Adapter<ListAdapter.NotesViewHolder>() {

    private var noteList = emptyList<Note>()
    private lateinit var noteViewModel: NoteViewModel

    inner class NotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        private var mMenus: ImageView

        init {
            title = itemView.findViewById(R.id.mTitle)
            description = itemView.findViewById(R.id.mSubTitle)
            mMenus = itemView.findViewById(R.id.mMenus)
            noteViewModel = ViewModelProvider(context as FragmentActivity).get(NoteViewModel::class.java)
            mMenus.setOnClickListener { popupMenus(it) }
    }
        // for delete popup and dialog
        private fun popupMenus(v:View) {
            val position: Int =  absoluteAdapterPosition
            val popupMenus = PopupMenu(context, v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener{
                when (it.itemId){
                    R.id.delete -> {
                        AlertDialog.Builder(context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("are you sure?")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                val deleteNote = noteList[position]
                                noteViewModel.deletebyId(deleteNote)
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true

                    }
                    else-> true
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible =  true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_note,parent,false)
        return NotesViewHolder(v)

    }

    override fun onBindViewHolder(holder: ListAdapter.NotesViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.title.text = currentNote.title
        holder.description.text = currentNote.description

        val id = currentNote.id
        val title = currentNote.title
        val description = currentNote.description

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity= v!!.context as FragmentActivity
                val etFragment = EditNoteFragment()
                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("des", description)
                bundle.putInt("position", id)
                etFragment.arguments = bundle
                activity.supportFragmentManager.commit{
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    setReorderingAllowed(true)
                    hide(activity.supportFragmentManager.findFragmentByTag("main_frag")!!)
                    add(R.id.fragmentContainer, etFragment)
                    addToBackStack(null)
                }
            }
        })

    }

    override fun getItemCount(): Int {
       return noteList.size
    }

    // use for setData by viewModel observer
    fun setData(note: List<Note>){
        this.noteList = note
        notifyDataSetChanged()
    }


}