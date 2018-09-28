package com.example.jvarani.hamburguer.model.value

import com.google.gson.annotations.SerializedName

data class Cart (
        val id: Int,
        @SerializedName("id_sandwich")
        val idSnack: Int,
        val extras: MutableList<Int>,
        val date: String = ""
)