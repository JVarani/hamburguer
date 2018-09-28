package com.example.jvarani.hamburguer.ui.snack.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.common.ItemOnClickListener
import com.example.jvarani.hamburguer.model.common.Utils.Mask
import com.example.jvarani.hamburguer.model.value.Ingredient
import kotlinx.android.synthetic.main.item_list_ingredient_adapter.view.*

class ViewHolderIngredient (view: View) : RecyclerView.ViewHolder(view) {
    val ivIngredient = view.iv_ingredient
    val tvName = view.tv_name
    val tvPrice = view.tv_price
    val rlAdd = view.rl_add
}

class ItemListIngredientAdapter(private val context : Context, val list: List<Ingredient>, val itemOnClickListener: ItemOnClickListener)
    : RecyclerView.Adapter<ViewHolderIngredient>(), View.OnClickListener {
    var pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIngredient {
        return ViewHolderIngredient(LayoutInflater.from(context).inflate(R.layout.item_list_ingredient_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderIngredient, position: Int) {
        val item = list[position]

        Glide.with(context)
                .load(item.image)
                .into(holder.ivIngredient)

        holder.tvName.text = item.name

        holder.tvPrice.text = Mask.FormataValor.formatarValor(item.price)
        holder.rlAdd.setOnClickListener {
            pos = position
            onClick(it)
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onClick(p0: View?) {
        itemOnClickListener.rowOnClick(p0, pos)
    }
}