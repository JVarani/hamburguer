package com.example.jvarani.hamburguer.ui.snack

import com.example.jvarani.hamburguer.model.value.Ingredient

interface SnackContract {
    interface View {
        fun loadIngredients(listIngredient: List<Ingredient>)
        fun emptyList()
        fun finishActivity()
    }

    interface Presenter {
        fun getIngredients()
    }
}