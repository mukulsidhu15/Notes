package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.notes.Fragments.AddNoteFragment
import com.example.notes.Fragments.NotesFragment


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NotesFragment>(R.id.fragmentContainer,"main_frag")
                addToBackStack(null)
                // addToBackStack(FragmentName::class.simpleName)
            }

        }
    }
}