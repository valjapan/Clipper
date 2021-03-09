package com.valjapan.clipper.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.valjapan.clipper.R
import com.valjapan.clipper.fragments.HistoryFragment

class MainActivity : AppCompatActivity() {

    private val historyFragment: HistoryFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sharedPreferences = getSharedPreferences("first_tutorial", Context.MODE_PRIVATE)
        val needTutorial = sharedPreferences.getBoolean("isFinishedTutorial", false)

        if (!needTutorial) {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

        if (savedInstanceState == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, historyFragment).commit()
        }

        val addButton: FloatingActionButton = findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        MobileAds.initialize(this) {}
        val adView: AdView = findViewById(R.id.ad_view)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

    }

}