package com.example.jvarani.hamburguer.ui.main

import android.util.Log
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.domain.repository.IRest
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    private lateinit var apiClient: APIClient
    private lateinit var iRest: IRest

    override fun getSnacks() {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getSnack: Call<List<Snack>> = iRest.getSnacks()
        getSnack.enqueue(object : Callback<List<Snack>> {
            override fun onFailure(call: Call<List<Snack>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Snack>>?, response: Response<List<Snack>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty())
                        view.loadListSnack(response.body()!!, false)
                    else
                        view.loadListSnack(response.body()!!, true)
                }
            }
        })
    }

    override fun getPromotions() {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getSnack: Call<List<Promotion>> = iRest.getPromotions()
        getSnack.enqueue(object : Callback<List<Promotion>> {
            override fun onFailure(call: Call<List<Promotion>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Promotion>>?, response: Response<List<Promotion>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty())
                        view.loadPromotion(response.body()!!, false)
                    else
                        view.loadPromotion(response.body()!!, true)
                }
            }
        })
    }

    fun expandableClick() {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getCart: Call<List<Cart>> = iRest.getCart()
        getCart.enqueue(object : Callback<List<Cart>> {
            override fun onFailure(call: Call<List<Cart>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Cart>>?, response: Response<List<Cart>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty())
                        view.expandableCartItens(response.body()!!)
                }
            }
        })
    }
}