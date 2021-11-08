package nl.dylandavid.project2.duoapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class NoteAdapter(fm: FragmentManager, lf: Lifecycle) :
    FragmentStateAdapter(fm, lf) {

    override fun getItemCount(): Int {
        return 39
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return NoteFragment()
            1 -> return NoteFragment()
            2 -> return NoteFragment()
            3 -> return NoteFragment()
            4 -> return NoteFragment()
            5 -> return NoteFragment()
            6 -> return NoteFragment()
            7 -> return NoteFragment()
            8 -> return NoteFragment()
            9 -> return NoteFragment()
            10 -> return NoteFragment()
            11 -> return NoteFragment()
            12 -> return NoteFragment()
            13 -> return NoteFragment()
            14 -> return NoteFragment()
            15 -> return NoteFragment()
            16 -> return NoteFragment()
            17 -> return NoteFragment()
            18 -> return NoteFragment()
            19 -> return NoteFragment()
            20 -> return NoteFragment()
            21 -> return NoteFragment()
            22 -> return NoteFragment()
            23 -> return NoteFragment()
            24 -> return NoteFragment()
            25 -> return NoteFragment()
            26 -> return NoteFragment()
            27 -> return NoteFragment()
            28 -> return NoteFragment()
            29 -> return NoteFragment()
            30 -> return NoteFragment()
            31 -> return NoteFragment()
            32 -> return NoteFragment()
            33 -> return NoteFragment()
            34 -> return NoteFragment()
            35 -> return NoteFragment()
            36 -> return NoteFragment()
            37 -> return NoteFragment()
            38 -> return NoteFragment()
        }
        return NoteFragment()
    }
}