package com.example.jvarani.hamburguer.model.common.Utils

import java.text.NumberFormat
import java.util.*

class CurrencyUtils {
    object CurrencyAmount {
        fun formatCurrencyAmount(amount: Any): String {
            var amount = amount
            amount = Utils.HalfUp.roundHalfUp(amount as Double, 2)
            val format = NumberFormat.getCurrencyInstance()
            return format.format(amount)
        }
    }

    object CurrencySymbol {
        fun getCurrencySymbol(): String {
            val currency = Currency.getInstance(Locale.getDefault())
            return currency.symbol
        }
    }
}