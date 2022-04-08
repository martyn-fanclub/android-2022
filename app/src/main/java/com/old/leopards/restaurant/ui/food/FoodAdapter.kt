package com.old.leopards.restaurant.ui.food

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.models.Food

class FoodAdapter(var foodList: List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.iv_foodPicture)
        val foodName: TextView = itemView.findViewById(R.id.tv_foodName)
        val foodWeight: TextView = itemView.findViewById(R.id.tv_foodWeight)
        val buttonFoodPrice: Button = itemView.findViewById(R.id.bt_foodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.foodName.text = foodItem.title
        //TODO
        holder.imageView.setImageResource(foodItem.img)
        holder.foodWeight.text = foodItem.weight.toString() + " гр."
        holder.buttonFoodPrice.text = foodItem.price.toString() + " р."

        holder.buttonFoodPrice.setOnClickListener {
            //TODO
        }
    }

    override fun getItemCount(): Int = foodList.size
}