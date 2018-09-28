package com.example.jvarani.hamburguer.domain.repository

import com.example.jvarani.hamburguer.model.value.*
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface IRest {

    //SNACK
    @GET("api/lanche")
    fun getSnacks() : Call<List<Snack>>

    //INGREDIENT
    @GET("api/ingrediente")
    fun getIngredients() : Call<List<Ingredient>>

    //CART
    @GET("api/pedido")
    fun getCart() : Call<List<Cart>>

    @PUT("api/pedido/{id}")
    fun bookHamburger(@Path("id") id: Int) : Call<ResponseBody>

    @PUT("api/pedido/{id}")
    fun bookHamburger(@Path("id") id: Int, @Body extras: JsonArray) : Call<ResponseBody>

    //PROMOTION
    @GET("api/promocao")
    fun getPromotions() : Call<List<Promotion>>
}