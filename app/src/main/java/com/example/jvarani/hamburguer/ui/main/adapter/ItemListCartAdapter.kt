package com.example.jvarani.hamburguer.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.value.Cart

class ViewHolderCart (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
}

class ItemListCartAdapter(private val context : Context, private val list : List<Cart>) : RecyclerView.Adapter<ViewHolderCart>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCart {
        return ViewHolderCart(LayoutInflater.from(context).inflate(R.layout.item_list_cart_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCart, position: Int) {
        val item = list[position]
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }
}