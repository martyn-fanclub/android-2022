package com.old.leopards.restaurant.models

import io.paperdb.Paper
import java.math.BigDecimal

class Cart {
    companion object {
        fun addItem(foodItem: Food): CartItem {
            val oldCart = getCart()
            val targetItem = oldCart.singleOrNull { it.food.id == foodItem.id }

            if (targetItem == null) {
                val newCart: MutableList<CartItem> = mutableListOf()
                newCart.addAll(oldCart)
                newCart.add(CartItem(foodItem, 1))
                saveCart(newCart)
                return newCart.single { it.food.id == foodItem.id }
            } else {
                targetItem.amount++
                saveCart(oldCart)
                return targetItem
            }
        }

        fun removeItem(foodItem: Food): CartItem? {
            val oldCart = getCart()
            val targetItem = oldCart.singleOrNull { it.food.id == foodItem.id }

            if (targetItem != null) {
                if (targetItem.amount > 1) {
                    targetItem.amount--
                    saveCart(oldCart)
                    return targetItem
                } else {
                    val newCart: MutableList<CartItem> = mutableListOf()
                    newCart.addAll(oldCart)
                    newCart.remove(targetItem)
                    saveCart(newCart)
                    return newCart.singleOrNull { it.food.id == foodItem.id }
                }
            } else {
                throw Error("trying to remove uncreated Item")
            }
        }

        private fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())!!
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.amount
            }

            return cartSize
        }

        fun getTotal(): BigDecimal {
            var total: BigDecimal = BigDecimal.ZERO;
            getCart().forEach {
                total = total.add(it.food.price.multiply(it.amount.toBigDecimal()))
            }
            return total
        }

        fun clearCart() {
            saveCart(mutableListOf())
        }
    }

    data class CartItem(
        val food: Food,
        var amount: Int
    ) {
        constructor(pair: Pair<Food, Int>) : this(pair.first, pair.second)
    }
}
