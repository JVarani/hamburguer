package com.example.jvarani.hamburguer.ui.snack

import android.util.Log
import com.example.jvarani.hamburguer.domain.repository.APIClient
import com.example.jvarani.hamburguer.domain.repository.IRest
import com.example.jvarani.hamburguer.model.value.Ingredient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SnackPresenter(val view: SnackContract.View) : SnackContract.Presenter {
    private lateinit var apiClient: APIClient
    private lateinit var iRest: IRest

    override fun getIngredients() {
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
                    if (response.body() != null && response.body()!!.isNotEmpty()) {
                        view.loadIngredients(response.body()!!)
                    } else
                        view.emptyList()
                } else {
                    view.emptyList()
                }
            }
        })
    }

    fun expandableClick(){
        view.expandableIngredientItens()
    }

    fun closeClick(){
        view.finishActivity()
    }

    fun saveClick(){
        view.saveSnack()
    }
}