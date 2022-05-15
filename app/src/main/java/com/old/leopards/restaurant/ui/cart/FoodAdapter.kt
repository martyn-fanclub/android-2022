package com.old.leopards.restaurant.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.models.Food

class FoodAdapter(var food: MutableList<Pair<Food, Int>>) : RecyclerView.Adapter<FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = food[position]
        holder.bind(food)
        holder.itemView.tag = food
    }

    override fun getItemCount(): Int {
        return food.size
    }
}

class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.cart_item_title)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.cart_item_description)
    private val priceTextView: TextView = itemView.findViewById(R.id.cart_item_price)
    private val weightTextView: TextView = itemView.findViewById(R.id.cart_item_weight)
    private val plusButton: Button = itemView.findViewById(R.id.cart_item_plus)
    private val minusButton: Button = itemView.findViewById(R.id.cart_item_minus)

    fun bind(foodEntry: Pair<Food, Int>) {
        val food = foodEntry.first
        titleTextView.text = food.title
        descriptionTextView.text = food.description
        priceTextView.text =
            itemView.context.getString(R.string.price_sum_template, foodEntry.second, food.price)
        weightTextView.text = food.title

        plusButton.setOnClickListener {
            foodEntry.second.inc()
        }

        minusButton.setOnClickListener {
            if (foodEntry.second == 1) {
                //TODO
            }
            foodEntry.second.dec()
        }

    }


}
