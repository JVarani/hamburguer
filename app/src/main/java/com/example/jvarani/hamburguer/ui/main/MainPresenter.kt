package com.example.jvarani.hamburguer.ui.main

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.john.capptan.extensions.showToastShort
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.domain.repository.IRest
import com.example.jvarani.hamburguer.model.common.Utils.Utils
import com.example.jvarani.hamburguer.model.event.CartEvent
import com.example.jvarani.hamburguer.model.value.*
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.jvarani.hamburguer.model.common.Utils.Utils.Json
import com.google.gson.*
import org.json.JSONObject
import java.lang.reflect.Array


class MainPresenter(val view: MainContract.View) : MainContract.Presenter {
    private lateinit var apiClient: APIClient
    private lateinit var iRest: IRest
    private lateinit var listSnack: List<Snack>
    private lateinit var listIngredient: List<Ingredient>

    override fun getSnacks() {
        apiClient = APIClient()
        iRest = apiClient.getApiClient()

        val getSnack: Call<List<Snack>> = iRest.getSnacks()
        getSnack.enqueue(object : Callback<List<Snack>> {
            override fun onFailure(call: Call<List<Snack>>?, t: Throwable?) {
                Log.d("error", t.toString())
                view.emptyList()
            }

            override fun onResponse(call: Call<List<Snack>>?, response: Response<List<Snack>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()){
                        listSnack = response.body()!!
                        getIngredients()
                    } else
                        view.emptyList()
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
                view.emptyList()
            }

            override fun onResponse(call: Call<List<Ingredient>>?, response: Response<List<Ingredient>>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body() != null && response.body()!!.isNotEmpty()){
                        listIngredient = response.body()!!
                        view.loadListSnack(listSnack, listIngredient)
                    }
                    else
                        view.emptyList()
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
                            EventBus.getDefault().post(CartEvent(response.body()!!))
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

        val arr = JsonArray(10)
        arr.add("1")
        if (Utils.GetIdSnackId.getIdSnackId(context, snack.id) == snack.id) {
            val setSnackToCar: Call<ResponseBody> = iRest.bookHamburger(snack.id, arr)
            setSnackToCar.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>?, response: retrofit2.Response<ResponseBody>?) {
                    if (response != null && response.isSuccessful) {
                        (context as Activity).showToastShort(snack.name + " Adicionado ao carrinho", false)
                        getListCart(true)
                    }
                }
            })
        } else {
            val setSnackToCar: Call<ResponseBody> = iRest.bookHamburger(snack.id)
            setSnackToCar.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    Log.d("error", t.toString())
                }

                override fun onResponse(call: Call<ResponseBody>?, response: retrofit2.Response<ResponseBody>?) {
                    if (response != null && response.isSuccessful) {
                        (context as Activity).showToastShort(snack.name + " Adicionado ao carrinho", false)
                        getListCart(true)
                    }
                }
            })
        }
    }
}