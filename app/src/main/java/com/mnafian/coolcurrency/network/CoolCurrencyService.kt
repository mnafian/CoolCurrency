package com.mnafian.coolcurrency.network

import com.scchao.currencytable.data.model.CurrencyRates
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created on : January/23/2020
 * Author     : mnafian
 * Project    : Cool Currency
 */
interface CoolCurrencyService {

    @GET("list")
    fun getCurrencyTypes(): Observable<CurrencyRates>

    @GET("live")
    fun getCurrencyRates(): Observable<CurrencyRates>
}