package com.example.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var coinViewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coinViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CoinViewModel::class.java)
//        coinViewModel.priceList.observe(this, Observer {
//            Log.d("test_",it.toString())
//        })

        coinViewModel.getDetailsInfo("BTC").observe(this, Observer {
            Log.d("test_detail",it.toString())
        })

    }


}