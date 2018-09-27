package com.example.jvarani.hamburguer.ui.main

import android.content.Context
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack

interface MainContract{
    interface View{
        fun loadListSnack(list: List<Snack>, listIngredient: List<Ingredient>, isEmpty: Boolean)
        fun loadPromotion(list: List<Promotion>, isEmpty: Boolean)
        fun expandableCartItens(listCart: List<Cart>, list: List<Snack>, listIngredient: List<Ingredient>)
    }

    interface Presenter{
        fun getSnacks()
        fun getPromotions()
        fun addSnackInCart(context: Context, snack: Snack)
        fun getListCart(isQuantity: Boolean)
    }
}