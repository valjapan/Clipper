package com.valjapan.clipper.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.valjapan.clipper.R

class Tutorial03Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView = inflater.inflate(R.layout.fragment_tutorial_03, container, false)

        return fragmentView
    }
}