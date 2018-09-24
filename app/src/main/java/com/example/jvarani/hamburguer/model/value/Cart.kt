package com.example.jvarani.hamburguer.model.value

data class Cart (
        val id: Int,
        val snackId: Int,
        val extras: List<Int>,
        val date: String = ""
)