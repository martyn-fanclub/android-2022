package com.old.leopards.restaurant.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.old.leopards.restaurant.R
import com.old.leopards.restaurant.database.entities.Order
import java.math.BigDecimal


class OrderAdapter :
    ListAdapter<Order, OrderViewHolder>(
        FlowerDiffCallback
    ) {

    fun interface OnItemsClickListener {
        fun onItemClick(price: BigDecimal)
    }

    var listener: OnItemsClickListener? = null

    fun setOnItemClickListener(listener: OnItemsClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }
}

class OrderViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val idTv: TextView = itemView.findViewById(R.id.order_item_id)
    private val dateTv: TextView = itemView.findViewById(R.id.order_item_date)
    private val priceTv: TextView = itemView.findViewById(R.id.order_item_price)

    fun bind(foodEntry: Order) {
        idTv.text = "Номер заказа: ${foodEntry.id.toString()}"
        dateTv.text = "${foodEntry.orderDate}"
        priceTv.text = "${foodEntry.price.toString()} руб."

    }

}


object FlowerDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}
