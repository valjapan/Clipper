package com.valjapan.clipper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.valjapan.clipper.R

class Tutorial02Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView = inflater.inflate(R.layout.fragment_tutorial_02, container, false)
        val image: ImageView = fragmentView.findViewById(R.id.icon_image_02)
        Glide.with(fragmentView.context).load(R.drawable.intro_gif).into(image)
        return fragmentView
    }
}