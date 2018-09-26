package com.example.jvarani.hamburguer.ui.main

import android.util.Log
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.domain.repository.IRest
import com.example.jvarani.hamburguer.model.value.Snack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(val view : MainContract.View) : MainContract.Presenter{
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
                if (response != null && response.isSuccessful){
                    if (response.body() != null && response.body()!!.isNotEmpty())
                        view.loadListSnack(response.body()!!, false)
                    else
                        view.loadListSnack(response.body()!!, true)
                }
            }
        })
    }
}