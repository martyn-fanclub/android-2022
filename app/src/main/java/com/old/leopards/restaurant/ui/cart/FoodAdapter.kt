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
import com.old.leopards.restaurant.models.Food

class FoodAdapter(private val onClick: (Pair<Food, Int>) -> Unit) :
    ListAdapter<Pair<Food, Int>, FoodViewHolder>(
        FlowerDiffCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return FoodViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
        holder.itemView.tag = food
    }
}


class FoodViewHolder(itemView: View, val onClick: (Pair<Food, Int>) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.cart_item_title)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.cart_item_description)
    private val priceTextView: TextView = itemView.findViewById(R.id.cart_item_price)
    private val weightTextView: TextView = itemView.findViewById(R.id.cart_item_weight)
    private val minusButton: Button = itemView.findViewById(R.id.cart_item_minus)
    private val plusButton: Button = itemView.findViewById(R.id.cart_item_plus)

    fun bind(foodEntry: Pair<Food, Int>) {
        val food = foodEntry.first
        titleTextView.text = food.title
        descriptionTextView.text = food.description
        priceTextView.text =
            itemView.context.getString(R.string.price_sum_template, foodEntry.second, food.price)
        weightTextView.text = food.title

        plusButton.setOnClickListener {
            foodEntry.second.inc()
            onClick(foodEntry)
        }

        minusButton.setOnClickListener {
            if (foodEntry.second == 1) {
                //TODO
            }
            foodEntry.second.dec()
            onClick(foodEntry)
        }
    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Pair<Food, Int>>() {
    override fun areItemsTheSame(oldItem: Pair<Food, Int>, newItem: Pair<Food, Int>): Boolean {
        return (oldItem.first.id == newItem.first.id) && (oldItem.second == newItem.second)
    }

    override fun areContentsTheSame(oldItem: Pair<Food, Int>, newItem: Pair<Food, Int>): Boolean {
        return oldItem == newItem
    }
}
