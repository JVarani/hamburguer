package com.example.jvarani.hamburguer.model.common

class Calculator() {
    object Calculator {
        fun getPromotionLight(value : Double) : Double {
            val ammount = (value*10)/100
            return value - ammount
        }

        fun getPromotionBeef(quantity: Int, value : Double) : Double {
            var result = 0
            var quant = quantity

            if (quant%3 == 0){
                result = quant/3
            }

            while (quant%3 != 0){
                result = quant/3
                quant--
            }

            result *= 2
            return (value*quant) - (value*result)
        }

        fun getPromotionCheese(quantity: Int, value : Double) : Double {
            var result = 0
            var quant = quantity

            if (quant%3 == 0){
                result = quant/3
            }

            while (quant%3 != 0){
                result = quant/3
                quant--
            }

            result *= 2
            return (value*quant) - (value*result)
        }
    }
}