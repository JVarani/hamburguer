package com.example.jvarani.hamburguer.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Snack
import kotlinx.android.synthetic.main.item_list_cart_adapter.view.*

class ViewHolderCart (view: View) : RecyclerView.ViewHolder(view) {
    val ivSnack = view.iv_snack
    val tvName = view.tv_name
    val tvQuantity = view.tv_quantity
    val tvPrice = view.tv_price
    val ivClose = view.iv_close
}

class ItemListCartAdapter(private val context : Context
                          , private val list : List<Cart>, private val listSnack: List<Snack>
                          , private val listIngredient: List<Ingredient>) : RecyclerView.Adapter<ViewHolderCart>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCart {
        return ViewHolderCart(LayoutInflater.from(context).inflate(R.layout.item_list_cart_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderCart, position: Int) {
        val item = list[position]

        for (snakc in listSnack){
            if (snakc.id != item.idSnack)
                continue

            Glide.with(context)
                    .load(snakc.image)
                    .into(holder.ivSnack)

            holder.tvName.text = snakc.name

            var total = 0.0
            for (ingredient in listIngredient){
                for (id in snakc.ingredients){
                    if (ingredient.id == id)
                        total += ingredient.price
                }
            }
            holder.tvPrice.text = total.toString()
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }
}