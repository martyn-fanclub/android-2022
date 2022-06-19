package com.old.leopards.restaurant.ui.food

import android.content.Context
import android.graphics.BitmapFactory
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
import com.old.leopards.restaurant.models.Cart
import com.old.leopards.restaurant.models.Food
import com.old.leopards.restaurant.ui.Global.Companion.showText
import kotlinx.coroutines.*

class FoodAdapter :
    ListAdapter<Food, FoodAdapter.FoodViewHolder>(FlowerDiffCallback) {

    class FoodViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var imageView: ImageView = itemView.findViewById(R.id.iv_food_picture)
        private val foodName: TextView = itemView.findViewById(R.id.tv_food_name)
        private val foodWeight: TextView = itemView.findViewById(R.id.tv_food_weight)
        private val buttonFoodPrice: Button = itemView.findViewById(R.id.bt_food_price)

        private var currentFood: Food? = null

        /* Bind food name and image. */
        @OptIn(DelicateCoroutinesApi::class)
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
                //imageView.setImageResource(foodItem.img)
                GlobalScope.launch(Dispatchers.IO) {
                    val `in` = java.net.URL(foodItem.img).openStream()
                    val bitmap = BitmapFactory.decodeStream(`in`)
                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                }
            } else {
                // FIXME set default img | for what? it is an error, need to rerun an app
                //imageView.setImageResource()
            }
            buttonFoodPrice.setOnClickListener {
                Cart.addItem(foodItem)
                showText(itemView.context, itemView.context.getString(R.string.add_food_to_cart_template, foodItem.title, Cart.getItemCount(foodItem.title)));
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
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
