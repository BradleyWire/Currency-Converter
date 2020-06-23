package com.bradley.wilson.currency.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import com.bradley.wilson.network.error.NoConnection
import com.bradley.wilson.network.error.ServerError

class CurrencyFeedViewModel(
    private val latestRatesUseCase: GetLatestRatesUseCase
) : ViewModel() {

    private val _currencyRatesFeedLiveData = MutableLiveData<List<Currency>>()
    val currencyFeedLiveData: LiveData<List<Currency>> = _currencyRatesFeedLiveData

    init {
        startFeed()
    }

    private fun startFeed(baseCurrency: String = DEFAULT_BASE_CURRENCY) =
        getLatestCurrencyRates(baseCurrency)

    private fun getLatestCurrencyRates(baseCurrency: String) {
        val params = GetLatestRatesParams(baseCurrency)
        latestRatesUseCase.execute(params, viewModelScope, POLLING_INTERVAL_MILLIS) {
            it.fold(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleFailure(failure: Failure) {
        val log = when (failure) {
            is NoConnection -> "Connection lost"
            is ServerError -> "Something is wrong with the network, pull down to try again."
            else -> "Unknown error, we're sorry for the inconvenience"
        }
        Log.e(TAG, log)
    }

    private fun handleSuccess(currencyRates: List<Currency>) {
        _currencyRatesFeedLiveData.postValue(currencyRates)
    }

    companion object {
        private const val TAG = "CurrencyFeedViewModel"
        private const val POLLING_INTERVAL_MILLIS = 1000L
        private const val DEFAULT_BASE_CURRENCY = "EUR"
    }
}