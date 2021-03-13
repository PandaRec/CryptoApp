package com.example.cryptoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_coin_details.*

class CoinDetailsActivity : AppCompatActivity() {

    private lateinit var coinViewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)
        coinViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(CoinViewModel::class.java)

        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)

        coinViewModel.getDetailsInfo(fromSymbol!!).observe(this, Observer {
            textViewDetailsFromSymbol.text = it.fromSymbol
            textViewDetailsToSymbol.text = it.toSymbol
            textViewDetailsPrice.text = it.price.toString()
            textViewDetailsMin.text = it.lowDay.toString()
            textViewDetailsMax.text = it.highDay.toString()
            textViewDetailsLastDeal.text = it.lastMarket
            textViewDetailsLastUpdate.text = it.getFormattedTime()
            Picasso.get().load(it.getFullImageUrl()).into(imageViewDetailsIcon)

        })


    }

    companion object{
        private const val EXTRA_FROM_SYMBOL="fSym"

        fun newIntent(context:Context, fromSymbol:String):Intent{
            val intent = Intent(context,CoinDetailsActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL,fromSymbol)
            return intent
        }
    }
}