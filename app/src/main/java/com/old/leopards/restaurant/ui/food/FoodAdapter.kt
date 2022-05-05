package com.old.leopards.restaurant.ui.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.models.Food

class FoodAdapter(private val onClick: (Food) -> Unit) :
    ListAdapter<Food, FoodAdapter.FoodViewHolder>(FlowerDiffCallback) {

    class FoodViewHolder(itemView: View, val onClick: (Food) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView = itemView.findViewById(R.id.iv_food_picture)
        private val foodName: TextView = itemView.findViewById(R.id.tv_food_name)
        private val foodWeight: TextView = itemView.findViewById(R.id.tv_food_weight)
        private val buttonFoodPrice: Button = itemView.findViewById(R.id.bt_food_price)

        private var currentFood: Food? = null

        init {
            itemView.setOnClickListener {
                currentFood?.let {
                    onClick(it)
                }
            }
        }

        /* Bind food name and image. */
        fun bind(foodItem: Food) {
            currentFood = foodItem

            foodName.text = foodItem.title
            foodWeight.text = itemView.context.getString(
                R.string.food_weight_template,
                foodItem.weight.toString()
            )
            buttonFoodPrice.text =
                itemView.context.getString(R.string.price_template, foodItem.price.toString())
            if (foodItem.img != null) {
                imageView.setImageResource(foodItem.img)
            } else {
                // FIXME set default img
                //imageView.setImageResource()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = getItem(position)
        holder.bind(foodItem)
    }

    object FlowerDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }
}
