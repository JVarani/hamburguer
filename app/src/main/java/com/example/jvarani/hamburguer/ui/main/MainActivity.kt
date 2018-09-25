package com.example.jvarani.hamburguer.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.databinding.MainBinding
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.model.value.Snack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var getSnack: Call<List<Snack>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBind = DataBindingUtil.setContentView<MainBinding>(this, R.layout.main)
        dataBind.presenter = MainPresenter()

        val apiClient = APIClient()
        val iRest = apiClient.getApiClient()
        getSnack = iRest.getSnacks()

        getSnack.enqueue(object : Callback<List<Snack>> {
            override fun onFailure(call: Call<List<Snack>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Snack>>?, response: Response<List<Snack>>?) {
                if (response != null && response.isSuccessful){
                    var list = response.body()

                    Log.d("teste", list!![0].name)
                }
            }
        })
    }
}