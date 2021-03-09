package com.valjapan.clipper.activities

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.valjapan.clipper.R
import com.valjapan.clipper.fragments.Tutorial01Fragment
import com.valjapan.clipper.fragments.Tutorial02Fragment
import com.valjapan.clipper.fragments.Tutorial03Fragment
import com.valjapan.clipper.fragments.Tutorial04Fragment

private const val NUM_PAGES = 4

class TutorialActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private val tutorial01Fragment = Tutorial01Fragment()
    private val tutorial02Fragment = Tutorial02Fragment()
    private val tutorial03Fragment = Tutorial03Fragment()
    private val tutorial04Fragment = Tutorial04Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        viewPager = findViewById(R.id.tutorial_pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 3) {
                    val sharedPreferences =
                        getSharedPreferences("first_tutorial", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isFinishedTutorial", true)
                    editor.apply()
                    finish()
                }
            }
        })

    }


    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> tutorial01Fragment
            1 -> tutorial02Fragment
            2 -> tutorial03Fragment
            else -> {
                tutorial04Fragment

            }
        }
    }
}