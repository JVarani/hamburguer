package com.example.jvarani.hamburguer.model.common

import android.view.View

interface ItemOnClickListener {
    fun rowOnClick(view: View?, position: Int)
}