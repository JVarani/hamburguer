package com.example.jvarani.hamburguer.ui.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.value.Promotion
import kotlinx.android.synthetic.main.item_list_promotion_adapter.view.*

class ViewHolderPromotion (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvName = view.tv_name
    val tvDescription = view.tv_description
}

class ItemListPromotionAdapter(private val context : Context, private val list : List<Promotion>) : RecyclerView.Adapter<ViewHolderPromotion>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPromotion {
        return ViewHolderPromotion(LayoutInflater.from(context).inflate(R.layout.item_list_promotion_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderPromotion, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.tvDescription.text = item.description
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return list.size
    }
}