package com.example.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*

class CoinPriceInfoAdapter: RecyclerView.Adapter<CoinPriceInfoAdapter.CoinPriceInfoViewHolder>() {
    var onCoinClickListener:OnCoinClickListener?=null
    var coinInfoList:List<CoinPriceInfo> = listOf()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPriceInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main,parent,false)
        return CoinPriceInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinPriceInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder){
            textViewTitle.text=coin.fromSymbol+" / "+coin.toSymbol
            textViewPrice.text = coin.price.toString()
            textViewLastUpdate.text = "Время последнего обновления: "+coin.getFormattedTime()
            Picasso.get().load(coin.getFullImageUrl()).into(imageViewIcon)
                itemView.setOnClickListener { onCoinClickListener?.onCoinClick(coin) }
        }
    }

    inner class CoinPriceInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageViewIcon = itemView.imageViewIcon
        val textViewTitle =  itemView.textViewTitle
        val textViewPrice = itemView.textViewDetailsPrice
        val textViewLastUpdate = itemView.textViewDetailsLastUpdate



    }

    interface OnCoinClickListener{
        fun onCoinClick(coinPriceInfo:CoinPriceInfo)
    }
}