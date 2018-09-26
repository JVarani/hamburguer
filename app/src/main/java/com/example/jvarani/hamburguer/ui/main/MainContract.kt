package com.example.jvarani.hamburguer.ui.main

import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack

interface MainContract{
    interface View{
        fun loadListSnack(list: List<Snack>, isEmpty: Boolean)
        fun loadPromotion(list: List<Promotion>, isEmpty: Boolean)
    }

    interface Presenter{
        fun getSnacks()
        fun getPromotions()
    }
}