package com.example.gitgraphdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.gitgraphdemo.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel.isLoading().observe(this, Observer {isLoading ->
            search_progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        mainActivityViewModel.getGitInfo().observe(this, Observer { profileInfo ->
            git_info.text = profileInfo.toString()
        })

        search_button.setOnClickListener {
            mainActivityViewModel.findRepositories(user_login.text.toString())
        }
    }
}
