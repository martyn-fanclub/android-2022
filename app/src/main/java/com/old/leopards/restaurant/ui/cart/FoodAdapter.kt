package com.old.leopards.restaurant.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.models.Cart
import java.math.BigDecimal

class FoodAdapter :
    ListAdapter<Cart.CartItem, FoodViewHolder>(
        FlowerDiffCallback
    ) {

    fun interface OnItemsClickListener {
        fun onItemClick(price: BigDecimal)
    }

    var listener: OnItemsClickListener? = null

    fun setOnItemClickListener(listener: OnItemsClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return FoodViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)

        holder.bind(food) { ->
            val currentList = currentList.toMutableList()
            currentList.removeAt(position)
            submitList(currentList)
        }
        holder.itemView.tag = food
    }

    fun isEmpty(): Boolean {
        return Cart.isEmpty();
    }

    fun clearCart() {
        Cart.clearCart()
        submitList(Cart.getCart())
    }

    fun getTotal(): BigDecimal {
        return Cart.getTotal()
    }

    fun pay(): BigDecimal {
        // TODO do some business logic
        val total = getTotal()
        clearCart()
        return total
    }
}

class FoodViewHolder(itemView: View, private val foodAdapter: FoodAdapter) :
    RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.cart_item_title)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.cart_item_description)
    private val priceTextView: TextView = itemView.findViewById(R.id.cart_item_price)
    private val weightTextView: TextView = itemView.findViewById(R.id.cart_item_weight)
    private val minusButton: Button = itemView.findViewById(R.id.cart_item_minus)
    private val plusButton: Button = itemView.findViewById(R.id.cart_item_plus)

    fun bind(foodEntry: Cart.CartItem, removeHolder: () -> Unit) {
        val food = foodEntry.food
        titleTextView.text = food.title
        descriptionTextView.text = food.description
        priceTextView.text =
            itemView.context.getString(R.string.price_sum_template, foodEntry.amount, food.price)
        weightTextView.text = itemView.context.getString(R.string.food_weight_template, food.weight.toString())

        plusButton.setOnClickListener {
            val addedItem = Cart.addItem(food)
            priceTextView.text =
                itemView.context.getString(
                    R.string.price_sum_template,
                    addedItem.amount,
                    addedItem.food.price
                )
            foodAdapter.listener?.onItemClick(foodAdapter.getTotal())
        }

        minusButton.setOnClickListener {
            val addedItem = Cart.removeItem(food)
            if (addedItem == null) {
                removeHolder()
            } else {
                priceTextView.text =
                    itemView.context.getString(
                        R.string.price_sum_template,
                        addedItem.amount,
                        addedItem.food.price
                    )
            }
            foodAdapter.listener?.onItemClick(foodAdapter.getTotal())
        }
    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Cart.CartItem>() {
    override fun areItemsTheSame(oldItem: Cart.CartItem, newItem: Cart.CartItem): Boolean {
        return (oldItem.food.id == newItem.food.id) && (oldItem.amount == newItem.amount)
    }

    override fun areContentsTheSame(oldItem: Cart.CartItem, newItem: Cart.CartItem): Boolean {
        return oldItem == newItem
    }
}
