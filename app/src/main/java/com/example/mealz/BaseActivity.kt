package com.example.mealz

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.mealz.databinding.ActivityBaseBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {
    private var _binding: ActivityBaseBinding? = null
    protected val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is MainActivity) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    true
                }

                R.id.Favorites -> {
                    if (this !is FavoriteActivity) {
                        startActivity(Intent(this, FavoriteActivity::class.java))
                        finish()
                    }
                    true
                }

                else -> {
                    false
                }
            }

        }
    }

    protected fun setChildBinding(childBinding: ViewBinding) {
        val contentArea = binding.root.findViewById<ViewGroup>(R.id.activity_content)
        contentArea.addView(childBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
