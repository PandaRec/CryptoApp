package com.example.cryptoapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cryptoapp.api.ApiFactory
import com.example.cryptoapp.database.AppDatabase
import com.example.cryptoapp.pojo.CoinInfoListOfData
import com.example.cryptoapp.pojo.CoinPriceInfo
import com.example.cryptoapp.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit



class CoinViewModel(application: Application): AndroidViewModel(application){
    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailsInfo(fSym:String):LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }
    init {
        loadData()
    }

    private fun loadData(){
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",")}
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it.toString()) }
            .map { getPriceListFromRawData(it) }
                .delaySubscription(Single.timer(3,TimeUnit.SECONDS))
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceInfoList(it)
                //Log.d("test_out", it.toString())
            },{
                Log.d("test_out",it.toString())

            })
        compositeDisposable.add(disposable)
    }

    fun getPriceListFromRawData(coinPriceInfoRawData: CoinPriceInfoRawData):List<CoinPriceInfo>{
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject
        val result = ArrayList<CoinPriceInfo>()
        if(jsonObject==null) return result
        val coinKeySet = jsonObject.keySet()
        for(coinKey in coinKeySet){
            val current = jsonObject.getAsJsonObject(coinKey)
            val currentKeySet = current.keySet()
            for(currentKey in currentKeySet){
                val res = Gson().fromJson(current.getAsJsonObject(currentKey),CoinPriceInfo::class.java)
                result.add(res)
            }
        }
        return result


    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}