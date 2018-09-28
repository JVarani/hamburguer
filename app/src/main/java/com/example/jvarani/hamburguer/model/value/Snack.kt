package com.example.jvarani.hamburguer.model.value

data class Snack (
        val id: Int,
        var name: String,
        var ingredients: MutableList<Int>,
        val image: String
)