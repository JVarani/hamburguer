package com.example.jvarani.hamburguer.model.common.Utils

import java.math.BigDecimal
import java.math.RoundingMode

class Utils {
    object HalfUp {
        fun roundHalfUp(value: Double, places: Int): Double {
            if (places < 0) throw IllegalArgumentException()
            var bd = BigDecimal(value)
            bd = bd.setScale(places, RoundingMode.HALF_UP)
            return bd.toDouble()
        }
    }
}