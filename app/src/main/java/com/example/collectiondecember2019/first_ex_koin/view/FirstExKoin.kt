package com.example.collectiondecember2019.first_ex_koin.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.collectiondecember2019.R
import com.example.collectiondecember2019.first_ex_koin.util.GridSpacingItemDecoration
import com.example.collectiondecember2019.first_ex_koin.view.adapter.MovieAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_first_ex.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FirstExKoin : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()
    private val picasso: Picasso by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_ex)
        init()
    }

    private fun init() {
        val adapter = MovieAdapter(picasso = picasso)
        recycler_view.apply {
            layoutManager = GridLayoutManager(this@FirstExKoin, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 50, true))
            this.adapter = adapter
        }
        movieViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            progress_bar.visibility = if (dataState.showProgress) View.VISIBLE else View.GONE
            if (dataState.movies != null && !dataState.movies.consumed) {
                dataState.movies.consume()?.let { movies -> adapter.submitList(movies) }
            }
            if (dataState.error != null && !dataState.error.consumed) {
                dataState.error.consume()
                    ?.let { errorMessage -> Log.e("error", " something happen with api") }
            }
        })
    }
}