package com.example.jvarani.hamburguer.model.common.Utils

class Mask() {
    object FormataValor{
        fun formatarValor(value: Any): String {
            return try {
                CurrencyUtils.CurrencyAmount.formatCurrencyAmount(value)
            } catch (e: Exception) {
                "$0,00"
            }

        }
    }
}