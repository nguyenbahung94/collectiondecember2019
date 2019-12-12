package com.example.collectiondecember2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collectiondecember2019.first_ex_koin.view.FirstExKoin
import com.example.collectiondecember2019.utils.openActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleEvent()
    }

    private fun handleEvent() {
        BtnOpenFirstEx.setOnClickListener {
            openActivity<FirstExKoin> { }
        }
    }
}
