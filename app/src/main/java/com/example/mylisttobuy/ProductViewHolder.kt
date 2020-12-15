package com.example.mylisttobuy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvName: TextView = itemView.findViewById(R.id.productName)
    var tvCost: TextView = itemView.findViewById(R.id.cost)
    var deleteContact: ImageView = itemView.findViewById(R.id.deleteProduct)
    var editContact: ImageView = itemView.findViewById(R.id.editProduct)
}