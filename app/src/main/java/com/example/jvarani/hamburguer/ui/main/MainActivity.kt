package com.example.jvarani.hamburguer.ui.main

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.databinding.MainBinding
import com.example.jvarani.hamburguer.model.common.ItemOnClickListener
import com.example.jvarani.hamburguer.model.common.Utils.Utils
import com.example.jvarani.hamburguer.model.event.AddSnackInCartEvent
import com.example.jvarani.hamburguer.model.event.LoadingPromotionEvent
import com.example.jvarani.hamburguer.model.event.LoadingSnackEvent
import com.example.jvarani.hamburguer.model.event.CartEvent
import com.example.jvarani.hamburguer.model.value.Cart
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.model.value.Promotion
import com.example.jvarani.hamburguer.model.value.Snack
import com.example.jvarani.hamburguer.ui.main.adapter.ItemListCartAdapter
import com.example.jvarani.hamburguer.ui.main.adapter.ItemListPromotionAdapter
import com.example.jvarani.hamburguer.ui.main.adapter.ItemListSnackAdapter
import com.example.jvarani.hamburguer.ui.snack.SnackActivity
import com.jrummyapps.android.animations.Technique
import kotlinx.android.synthetic.main.bottom_sheet_promotion.*
import kotlinx.android.synthetic.main.main.*
import net.cachapa.expandablelayout.ExpandableLinearLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var dataBind: MainBinding
    lateinit var expandable: ExpandableLinearLayout
    lateinit var ivArrow: ImageView
    private lateinit var listSnack: List<Snack>
    private lateinit var listIngre: List<Ingredient>

    val EDIT_ITEM = 1
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.main)
        dataBind.presenter = MainPresenter(this)
        expandable = findViewById(R.id.expandable_layout_cart_itens)
        ivArrow = findViewById(R.id.iv_arrow)
    }

    override fun loadListSnack(list: List<Snack>, listIngredient: List<Ingredient>) {
        if (isUpdate)
            return

        tv_empty.visibility = View.GONE
        rl_pb_loading.visibility = View.GONE
        rv.visibility = View.VISIBLE

        listSnack = list
        listIngre = listIngredient
        dataBind.presenter!!.getListCart(true)

        val adapter = ItemListSnackAdapter(this, list, listIngredient)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun emptyList() {
        tv_empty.visibility = View.VISIBLE
        rl_pb_loading.visibility = View.GONE
        rv.visibility = View.GONE
    }

    override fun loadPromotion(list: List<Promotion>, isEmpty: Boolean) {
        val adapter = ItemListPromotionAdapter(this, list)
        rv_promotion.layoutManager = LinearLayoutManager(this)
        rv_promotion.adapter = adapter
    }

    override fun expandableCartItens(listCart: List<Cart>, listSnack: List<Snack>, listIngredient: List<Ingredient>) {
        if (listCart.isNotEmpty() || expandable.isExpanded) {
            expandable.toggle()
            expandable.bringToFront()
            if (expandable.isExpanded) {
                Technique.FADE_IN.playOn(tv_expandable)
                ivArrow.setImageResource(R.drawable.ic_arrow_up_pink_24dp)
                Technique.FADE_IN.composer.duration(256).playOn(iv_arrow)

                val adapter = ItemListCartAdapter(this, listCart, listSnack, listIngredient)
                rv_cart.layoutManager = LinearLayoutManager(this)
                rv_cart.adapter = adapter
            } else {
                Technique.FADE_OUT.composer.duration(384).playOn(tv_expandable)
                ivArrow.setImageResource(R.drawable.ic_arrow_down_pink_24dp)
                Technique.FADE_IN.composer.duration(256).playOn(iv_arrow)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onLoadingSnacks(event: LoadingSnackEvent) {
        dataBind.presenter!!.getSnacks()
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onLoadingPromotion(event: LoadingPromotionEvent) {
        dataBind.presenter!!.getPromotions()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddSnackInCart(event: AddSnackInCartEvent) {
        dataBind.presenter!!.addSnackInCart(this, event.snack)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onText(event: CartEvent) {
        tv_counter.text = event.list.size.toString()

        for (snack in listSnack) {
            if (snack.id == event.list[event.list.size-1].idSnack){
                tv_item_cart.text = snack.name
            }
        }
    }

    private fun changeSnack() {
        for ((count, item) in listSnack.withIndex()) {
            if (Utils.GetIdSnack.getIdSnack(this) == item.id) {
                listSnack[count].name = listSnack[count].name.substringBefore(' ') + " - do seu jeito"

                for (ingredient in Utils.GetListIngredient.getListIngredient(this))
                listSnack.toMutableList()[count].ingredients.add(ingredient.id)
            }
        }
        val adapter = ItemListSnackAdapter(this, listSnack, listIngre)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        EventBus.getDefault().post(LoadingSnackEvent())
        EventBus.getDefault().post(LoadingPromotionEvent())
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_ITEM){
            if (resultCode == Activity.RESULT_OK){
                isUpdate = true
                changeSnack()
            }
        }
    }
}