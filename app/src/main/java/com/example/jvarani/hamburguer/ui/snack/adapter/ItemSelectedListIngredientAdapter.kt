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
import kotlinx.android.synthetic.main.item_selected_list_ingredient_adapter.view.*

class ViewHolderSelectedIngredient (view: View) : RecyclerView.ViewHolder(view) {
    val ivIngredient = view.iv_ingredient
    val tvName = view.tv_name
    val tvPrice = view.tv_price
    val ivClose = view.iv_close
}

class ItemSelectedListIngredientAdapter(private val context : Context, private val list: MutableList<Ingredient>
                                        , private val itemOnClickListener: ItemOnClickListener) : RecyclerView.Adapter<ViewHolderSelectedIngredient>(), View.OnClickListener {
    private var pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSelectedIngredient {
        return ViewHolderSelectedIngredient(LayoutInflater.from(context).inflate(R.layout.item_selected_list_ingredient_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderSelectedIngredient, position: Int) {
        val item = list[position]

        Glide.with(context)
                .load(item.image)
                .into(holder.ivIngredient)

        holder.tvName.text = item.name
        holder.tvPrice.text = Mask.FormataValor.formatarValor(item.price)

        holder.ivClose.setOnClickListener {
            pos = position
            onClick(it)
            notifyDataSetChanged()
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onClick(view: View?) {
        itemOnClickListener.rowOnClick(view, pos)
    }
}