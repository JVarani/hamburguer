package com.example.jvarani.hamburguer.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.event.AddSnackInCartEvent
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Snack
import kotlinx.android.synthetic.main.item_list_snack_adapter.view.*
import org.greenrobot.eventbus.EventBus

class ViewHolderSnack (view: View) : RecyclerView.ViewHolder(view) {
    val ivSnack = view.iv_snack
    val tvName = view.tv_name
    val tvPrice = view.tv_price
    val rlAdd = view.rl_add
    val rlEdit = view.rl_edit
    val ibCart = view.ib_cart
    val ibEdit = view.ib_edit
}

class ItemListSnackAdapter(private val context : Context, private val list : List<Snack>, val listIngredient: List<Ingredient>) : RecyclerView.Adapter<ViewHolderSnack>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSnack {
        return ViewHolderSnack(LayoutInflater.from(context).inflate(R.layout.item_list_snack_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderSnack, position: Int) {
        val item = list[position]

        Glide.with(context)
                .load(item.image)
                .into(holder.ivSnack)

        holder.tvName.text = item.name

        var total = 0.0
        for (ingredient in listIngredient){
            for (id in item.ingredients){
                if (ingredient.id == id)
                    total += ingredient.price
            }
        }

        holder.tvPrice.text = total.toString()
        holder.rlAdd.setOnClickListener {
            addSnackToCart(item)
        }

        holder.ibCart.setOnClickListener {
            addSnackToCart(item)
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