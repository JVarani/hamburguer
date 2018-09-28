package com.example.jvarani.hamburguer.model.common.Utils

import android.annotation.SuppressLint
import android.content.Context
import java.math.BigDecimal
import java.math.RoundingMode
import com.google.gson.Gson
import android.content.Context.MODE_PRIVATE
import com.example.jvarani.hamburguer.model.value.Ingredient
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive




class Utils {
    object HalfUp {
        fun roundHalfUp(value: Double, places: Int): Double {
            if (places < 0) throw IllegalArgumentException()
            var bd = BigDecimal(value)
            bd = bd.setScale(places, RoundingMode.HALF_UP)
            return bd.toDouble()
        }
    }

    object SetIdSnack {
        fun setIdSnack (context: Context, id: Int) {
            val pref = context.getSharedPreferences("snack", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putInt("id", id)
            editor.apply()
        }
    }

    object GetIdSnack {
        fun getIdSnack (context: Context): Int{
            val pref = context.getSharedPreferences("snack", MODE_PRIVATE)
            return pref.getInt("id", -1)
        }
    }

    object SetIdSnackId {
        fun setIdSnackId (context: Context, id: Int) {
            val pref = context.getSharedPreferences("snack$id", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putInt("id", id)
            editor.apply()
        }
    }

    object GetIdSnackId {
        fun getIdSnackId (context: Context, id: Int): Int{
            val pref = context.getSharedPreferences("snack$id", MODE_PRIVATE)
            return pref.getInt("id", -1)
        }
    }

    object SetListIngredient {
        fun setListIngredient (context: Context, list: MutableList<Ingredient>) {
            val pref = context.getSharedPreferences("ingredient", MODE_PRIVATE)
            val editor = pref.edit()

            val gson = Gson()
            val json = gson.toJson(list)

            editor.putString("list", json)
            editor.apply()
        }
    }

    object GetListIngredient {
        @SuppressLint("CommitPrefEdits")
        fun getListIngredient (context: Context): MutableList<Ingredient>{
            val pref = context.getSharedPreferences("ingredient", MODE_PRIVATE)

            val gson = Gson()
            val json = pref.getString("list", "")
            val type = object : TypeToken<MutableList<Ingredient>>() {

            }.type
            return gson.fromJson(json, type)
        }
    }

    object Json {
        fun createJsonArrayFromList(list: List<Int>): JsonArray {
            val gson = Gson()
            val element = gson.toJsonTree(list, object : TypeToken<List<Int>>() {

            }.type)

            return element.asJsonArray
        }
    }
}