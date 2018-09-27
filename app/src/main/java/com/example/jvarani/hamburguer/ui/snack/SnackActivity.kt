package com.example.jvarani.hamburguer.ui.snack

import android.databinding.DataBindingUtil
import com.example.jvarani.hamburguer.databinding.SnackBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.common.ItemOnClickListener
import com.example.jvarani.hamburguer.model.common.Utils.Mask
import com.example.jvarani.hamburguer.model.event.LoadingIngredientEvent
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.ui.snack.adapter.ItemListIngredientAdapter
import kotlinx.android.synthetic.main.snack.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SnackActivity : AppCompatActivity(), SnackContract.View {
    private lateinit var dataBind: SnackBinding
    private var value: Double = 0.0
    private var id: Int = 0
    private val list = mutableListOf<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.snack)
        dataBind.presenter = SnackPresenter(this)

        value = intent.getDoubleExtra("value", -1.0)
        id = intent.getIntExtra("id", -1)
        tv_name.text = intent.getStringExtra("name")
        tv_price.text = Mask.FormataValor.formatarValor(value)
    }

    override fun loadIngredients(listIngredient: List<Ingredient>) {
        rl_pb_loading.visibility = View.GONE
        tv_empty.visibility = View.GONE
        val adapter = ItemListIngredientAdapter(this, listIngredient, object : ItemOnClickListener {
            override fun rowOnClick(view: View?, position: Int) {
                list.add(listIngredient[position])
                tv_counter.text = list.size.toString()
                tv_item_ingredient.text = listIngredient[position].name
                value += listIngredient[position].price
                tv_price.text = Mask.FormataValor.formatarValor(value)
            }
        })
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun emptyList() {
        tv_empty.visibility = View.VISIBLE
        rl_pb_loading.visibility = View.GONE
    }

    override fun finishActivity() {
        finish()
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onLoadingIngredients(event: LoadingIngredientEvent) {
        dataBind.presenter!!.getIngredients()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        EventBus.getDefault().post(LoadingIngredientEvent())
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}