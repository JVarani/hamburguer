package com.example.jvarani.hamburguer.ui.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.domain.repository.IRest
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    private lateinit var apiClient: APIClient
    private lateinit var iRest: IRest
    private lateinit var listSnack: List<Snack>
    private lateinit var listIngredient: List<Ingredient>

    var quantity: String = "0"

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
                    if (response.body() != null && response.body()!!.isNotEmpty()){
                        listSnack = response.body()!!
                        getIngredients()
                    }
                }
            }
        })
    }

    fun getIngredients() {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getIngredient: Call<List<Ingredient>> = iRest.getIngredients()
        getIngredient.enqueue(object : Callback<List<Ingredient>> {
            override fun onFailure(call: Call<List<Ingredient>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Ingredient>>?, response: Response<List<Ingredient>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()){
                        listIngredient = response.body()!!
                        view.loadListSnack(listSnack, listIngredient, false)
                    }
                    else
                        view.loadListSnack(listSnack, listIngredient, true)
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
        getListCart(false)
    }

    override fun getListCart(isQuantity: Boolean) {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getCart: Call<List<Cart>> = iRest.getCart()
        getCart.enqueue(object : Callback<List<Cart>> {
            override fun onFailure(call: Call<List<Cart>>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<List<Cart>>?, response: Response<List<Cart>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()){
                        if (isQuantity)
                            quantity = (response.body()!!.size + 1).toString()
                        else
                            view.expandableCartItens(response.body()!!, listSnack, listIngredient)
                    }
                }
            }
        })
    }

    override fun addSnackInCart(context: Context, snack: Snack) {
        val apiClient = APIClient()
        val iRest = apiClient.getApiClient()

        val setSnackToCar: Call<ResponseBody> = iRest.bookHamburger(snack.id)
        setSnackToCar.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("error", t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>?, response: retrofit2.Response<ResponseBody>?) {
                if (response != null && response.isSuccessful)
                    Toast.makeText(context, snack.name + " Adicionado ao carrinho", Toast.LENGTH_SHORT).show()
            }
        })
    }
}