package com.example.jvarani.hamburguer.ui.snack

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import com.example.jvarani.hamburguer.databinding.SnackBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.example.john.capptan.extensions.showToastShort
import com.example.jvarani.hamburguer.R
import com.example.jvarani.hamburguer.model.common.ItemOnClickListener
import com.example.jvarani.hamburguer.model.common.Utils.Mask
import com.example.jvarani.hamburguer.model.common.Utils.Utils
import com.example.jvarani.hamburguer.model.event.LoadingIngredientEvent
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.example.jvarani.hamburguer.ui.snack.adapter.ItemListIngredientAdapter
import com.example.jvarani.hamburguer.ui.snack.adapter.ItemSelectedListIngredientAdapter
import com.jrummyapps.android.animations.Technique
import kotlinx.android.synthetic.main.snack.*
import net.cachapa.expandablelayout.ExpandableLinearLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SnackActivity : AppCompatActivity(), SnackContract.View {
    private lateinit var dataBind: SnackBinding
    private var value: Double = 0.0
    private var id: Int = 0
    private val list = mutableListOf<Ingredient>()
    private lateinit var expandable: ExpandableLinearLayout
    private lateinit var ivArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.snack)
        dataBind.presenter = SnackPresenter(this)

        value = intent.getDoubleExtra("value", -1.0)
        id = intent.getIntExtra("id", -1)
        tv_name.text = intent.getStringExtra("name")
        tv_price.text = Mask.FormataValor.formatarValor(value)
        expandable = findViewById(R.id.expandable_layout_ingredient_itens)
        ivArrow = findViewById(R.id.iv_arrow)
    }

    override fun loadIngredients(listIngredient: List<Ingredient>) {
        rl_pb_loading.visibility = View.GONE
        tv_empty.visibility = View.GONE
        rv_ingredient.visibility = View.VISIBLE
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

    override fun expandableIngredientItens() {
        if (list.isNotEmpty() || expandable.isExpanded) {
            expandable.toggle()
            expandable.bringToFront()
            if (expandable.isExpanded) {
                Technique.FADE_IN.playOn(tv_expandable)
                ivArrow.setImageResource(R.drawable.ic_arrow_up_pink_24dp)
                Technique.FADE_IN.composer.duration(256).playOn(iv_arrow)

                val adapter = ItemSelectedListIngredientAdapter(this, list, object:ItemOnClickListener{
                    override fun rowOnClick(view: View?, position: Int) {
                        if (list.size == 1) {
                            expandableIngredientItens()
                            tv_item_ingredient.text = getString(R.string.no_ingredient_added)
                        } else
                            tv_item_ingredient.text = list[list.size-1].name

                        value -= list.removeAt(position).price
                        tv_counter.text = list.size.toString()
                        tv_price.text = Mask.FormataValor.formatarValor(value)                    }
                })

                rv_ingredient.layoutManager = LinearLayoutManager(this)
                rv_ingredient.adapter = adapter
            } else {
                Technique.FADE_OUT.composer.duration(384).playOn(tv_expandable)
                ivArrow.setImageResource(R.drawable.ic_arrow_down_pink_24dp)
                Technique.FADE_IN.composer.duration(256).playOn(iv_arrow)
            }
        }
    }

    override fun emptyList() {
        tv_empty.visibility = View.VISIBLE
        rl_pb_loading.visibility = View.GONE
        rv_ingredient.visibility = View.GONE
    }

    override fun finishActivity() {
        finish()
    }

    override fun saveSnack() {
        if (list.size < 1) {
            this.showToastShort("Adicione um ingrediente na lista por favor", false)
            return
        }

        Utils.SetListIngredient.setListIngredient(this, list)
        setResult(Activity.RESULT_OK)
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