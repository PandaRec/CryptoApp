package com.example.cryptoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.adapters.CoinPriceInfoAdapter
import com.example.cryptoapp.pojo.CoinPriceInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var coinViewModel: CoinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CoinPriceInfoAdapter()
        recyclerViewMain.adapter = adapter

        coinViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CoinViewModel::class.java)
        coinViewModel.priceList.observe(this, Observer {
            //Log.d("test_",it.toString())
            adapter.coinInfoList= it
        })

        adapter.onCoinClickListener = object :CoinPriceInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                Log.d("test_",coinPriceInfo.fromSymbol)
                val intent = CoinDetailsActivity.newIntent(this@MainActivity,coinPriceInfo.fromSymbol)
                startActivity(intent)
            }
        }






//        coinViewModel.getDetailsInfo("BTC").observe(this, Observer {
//            Log.d("test_detail",it.toString())
//        })

    }


}