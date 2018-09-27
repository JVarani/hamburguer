package com.example.jvarani.hamburguer.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.databinding.MainBinding
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
    lateinit var listSnack: List<Snack>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.main)
        dataBind.presenter = MainPresenter(this)
        expandable = findViewById(R.id.expandable_layout_cart_itens)
        ivArrow = findViewById(R.id.iv_arrow)
    }

    override fun loadListSnack(list: List<Snack>, listIngredient: List<Ingredient>, isEmpty: Boolean) {
        if (isEmpty) {
            tv_empty.visibility = View.VISIBLE
            return
        }
        listSnack = list
        dataBind.presenter!!.getListCart(true)

        rl_pb_loading.visibility = View.GONE
        tv_empty.visibility = View.GONE
        val adapter = ItemListSnackAdapter(this, list, listIngredient)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
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
}