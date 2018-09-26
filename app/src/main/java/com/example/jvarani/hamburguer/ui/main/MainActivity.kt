package com.example.jvarani.hamburguer.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.databinding.MainBinding
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack
import com.example.jvarani.hamburguer.ui.main.adapter.ItemListPromotionAdapter
import com.example.jvarani.hamburguer.ui.main.adapter.ItemListSnackAdapter
import kotlinx.android.synthetic.main.bottom_sheet_promotion.*
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var dataBind : MainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.main)
        dataBind.presenter = MainPresenter(this)
    }

    override fun loadListSnack(list: List<Snack>, isEmpty: Boolean) {
        if (isEmpty) {
            tv_empty.visibility = View.VISIBLE
            return
        }

        rl_pb_loading.visibility = View.GONE
        tv_empty.visibility = View.GONE
        val listIngredient = mutableListOf<Ingredient>()
        val adapter = ItemListSnackAdapter(this, list, listIngredient)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun loadPromotion(list: List<Promotion>, isEmpty: Boolean) {
        val adapter = ItemListPromotionAdapter(this, list)
        rv_promotion.layoutManager = LinearLayoutManager(this)
        rv_promotion.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dataBind.presenter!!.getPromotions()
        dataBind.presenter!!.getSnacks()
    }
}