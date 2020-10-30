package com.aps.assignment.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aps.assignment.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Check for instance and then add main fragment
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, ResultFragment.newInstance())
                .commit()
        }
    }

    fun setToolBarTitle(title: String) {
        //To set toolbar title from the fragment classes
        supportActionBar?.let {
            it.title = title
        }
    }

    open fun launchFragment(fragment: Fragment, title: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content_frame, fragment, title)
            .addToBackStack(title)
            .commitAllowingStateLoss()
    }
}