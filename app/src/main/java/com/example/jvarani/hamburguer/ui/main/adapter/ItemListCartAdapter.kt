package com.example.jvarani.hamburguer.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.common.Calculator
import com.example.jvarani.hamburguer.model.common.Utils.Mask
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Snack
import kotlinx.android.synthetic.main.item_list_cart_adapter.view.*

class ViewHolderCart (view: View) : RecyclerView.ViewHolder(view) {
    val ivSnack = view.iv_snack
    val tvName = view.tv_name
    val tvPrice = view.tv_price
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

            if (item.extras.size > 1) {
                if (item.idSnack == snakc.id)
                    snakc.ingredients = item.extras
            }

            if (snakc.id != item.idSnack)
                continue

            Glide.with(context)
                    .load(snakc.image)
                    .into(holder.ivSnack)

            holder.tvName.text = snakc.name

            for (ingredient in snakc.ingredients){
                if (ingredient == 1 ){

                }
            }

            var total = 0.0
            var valueBeef = 0.0
            var valueCheese = 0.0

            for (ingredient in listIngredient){
                for (id in snakc.ingredients){
                    if (ingredient.id == id)
                        total += ingredient.price

                    if (ingredient.id == 3)
                        valueBeef = ingredient.price
                    else if (ingredient.id == 5)
                        valueCheese = ingredient.price
                }
            }

            if (snakc.ingredients.contains(1) && !snakc.ingredients.contains(2)){
                total = Calculator.Calculator.getPromotionLight(total)
            }
            var countBeef = 0
            var countCheese = 0
            for (ingredient in snakc.ingredients){
                if (ingredient == 3)
                    countBeef++
                else if (ingredient == 5)
                    countCheese++

            }

            if (countBeef >= 3)
                total = Calculator.Calculator.getPromotionBeef(countBeef, valueBeef)

            if (countCheese >= 3)
                total = Calculator.Calculator.getPromotionCheese(countCheese, valueCheese)

            holder.tvPrice.text = Mask.FormataValor.formatarValor(total)
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }
}