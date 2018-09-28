package com.example.jvarani.hamburguer.ui.main.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.common.Calculator
import com.example.jvarani.hamburguer.model.common.Utils.Mask
import com.example.jvarani.hamburguer.model.common.Utils.Utils
import com.example.jvarani.hamburguer.model.event.AddSnackInCartEvent
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Snack
import com.example.jvarani.hamburguer.ui.snack.SnackActivity
import kotlinx.android.synthetic.main.item_list_snack_adapter.view.*
import org.greenrobot.eventbus.EventBus

class ViewHolderSnack (view: View) : RecyclerView.ViewHolder(view) {
    val ivSnack = view.iv_snack
    val tvName = view.tv_name
    val tvPrice = view.tv_price
    val rlAdd = view.rl_add
    val rlEdit = view.rl_edit
}

class ItemListSnackAdapter(private val context : Context, private val list : List<Snack>, private val listIngredient: List<Ingredient>) : RecyclerView.Adapter<ViewHolderSnack>() {
    val EDIT_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSnack {
        return ViewHolderSnack(LayoutInflater.from(context).inflate(R.layout.item_list_snack_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderSnack, position: Int) {
        val item = list[position]

        Glide.with(context)
                .load(item.image)
                .into(holder.ivSnack)

        holder.tvName.text = item.name

        for (ingredient in item.ingredients){
            if (ingredient == 1 ){

            }
        }

        var total = 0.0
        var valueBeef = 0.0
        var valueCheese = 0.0

        for (ingredient in listIngredient){
            for (id in item.ingredients){
                if (ingredient.id == id)
                    total += ingredient.price

                if (ingredient.id == 3)
                    valueBeef = ingredient.price
                else if (ingredient.id == 5)
                    valueCheese = ingredient.price
            }
        }

        if (item.ingredients.contains(1) && !item.ingredients.contains(2)){
            total = Calculator.Calculator.getPromotionLight(total)
        }
        var countBeef = 0
        var countCheese = 0
        for (ingredient in item.ingredients){
            if (ingredient == 3)
                countBeef++
            else if (ingredient == 5)
                countCheese++

        }

        if (countBeef >= 3)
            total -= Calculator.Calculator.getPromotionBeef(countBeef, valueBeef)

        if (countCheese >= 3)
            total -= Calculator.Calculator.getPromotionCheese(countCheese, valueCheese)

        holder.tvPrice.text = Mask.FormataValor.formatarValor(total)
        holder.rlAdd.setOnClickListener {
            addSnackToCart(item)
        }

        holder.rlEdit.setOnClickListener {
            Utils.SetIdSnack.setIdSnack(context, item.id)
            Utils.SetIdSnackId.setIdSnackId(context, item.id)
            (context as Activity).startActivityForResult(Intent(context, SnackActivity::class.java)
                    .putExtra("name", item.name)
                    .putExtra("value", total)
                    .putExtra("id", item.id), EDIT_ITEM)
        }
    }

    private fun addSnackToCart(snack: Snack) {
        EventBus.getDefault().post(AddSnackInCartEvent(snack))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }
}