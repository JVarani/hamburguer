package com.example.jvarani.hamburguer

import com.example.jvarani.hamburguer.model.common.Calculator
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class CalculatorTest : Spek({

    given("Some calculator") {

        on("Light") {
            val light = Calculator.Calculator.getPromotionLight(100.0)

            it("Should return a price in light offer") {
                assertEquals(90.0, light)
            }
        }

        on("Beef") {
            val beef = Calculator.Calculator.getPromotionBeef(3, 1.00)

            it("Should return a price in much meat offer") {
                assertEquals(1.00, beef)
            }
        }

        on("Cheese") {
            val cheese = Calculator.Calculator.getPromotionCheese(3, 1.00)

            it("Should return a price in much cheese offer") {
                assertEquals(1.00, cheese)
            }
        }
    }
})