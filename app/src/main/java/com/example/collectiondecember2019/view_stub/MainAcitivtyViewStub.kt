package com.example.collectiondecember2019.view_stub

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.collectiondecember2019.R
import kotlinx.android.synthetic.main.activity_view_stub.*


class MainAcitivtyViewStub : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)

        buttonShow.setOnClickListener {
            showViewStub()
        }
        buttonHide.setOnClickListener {
            hideViewStub()
        }
    }

    private fun showViewStub() {
        // If you want to change data of ViewStub at runtime, you can do like this
        val inflatedView = viewStub.inflate()
        val textViewInViewStub: TextView = inflatedView.findViewById(R.id.textInViewStub)
        textViewInViewStub.text = "ABC"
        viewStub.visibility = View.VISIBLE
    }

    private fun hideViewStub() {
        viewStub.visibility = View.GONE
    }
}