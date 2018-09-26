package com.example.jvarani.hamburguer.ui.main

import com.example.jvarani.hamburguer.model.value.Snack

interface MainContract{
    interface View{
        fun loadListSnack(list: List<Snack>, isEmpty: Boolean)
    }

    interface Presenter{
        fun getSnacks()
    }
}