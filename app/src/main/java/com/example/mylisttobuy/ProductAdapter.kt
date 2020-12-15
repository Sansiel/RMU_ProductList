package com.example.mylisttobuy

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.util.*
internal class ProductAdapter(private val context: Context, listContacts: ArrayList<Product>) :
    RecyclerView.Adapter<ProductViewHolder>(), Filterable {
    private var listProduct: ArrayList<Product>
    private val mArrayList: ArrayList<Product>
    private val mDatabase: SqliteDatabase
    init {
        this.listProduct = listContacts
        this.mArrayList = listContacts
        mDatabase = SqliteDatabase(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout, parent, false)
        return ProductViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = listProduct[position]
        holder.tvName.text = product.name
        holder.tvCost.text = product.cost
        holder.editContact.setOnClickListener { editTaskDialog(product) }
        holder.deleteContact.setOnClickListener {
            mDatabase.deleteProduct(product.id)
            (context as Activity).finish()
            context.startActivity(context.intent)
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                listProduct = if (charString.isEmpty()) {
                    mArrayList
                }
                else {
                    val filteredList = ArrayList<Product>()
                    for (product in mArrayList) {
                        if (product.name.toLowerCase().contains(charString)) {
                            filteredList.add(product)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listProduct
                return filterResults
            }
            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            )
            {
                listProduct =
                    filterResults.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }
    override fun getItemCount(): Int {
        return listProduct.size
    }
    private fun editTaskDialog(product: Product) {
        val inflater = LayoutInflater.from(context)
        val subView = inflater.inflate(R.layout.add_product, null)
        val nameField: EditText = subView.findViewById(R.id.enterName)
        val costField: EditText = subView.findViewById(R.id.enterCost)
        nameField.setText(product.name)
        costField.setText(product.cost)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit product")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton(
            "EDIT PRODUCT"
        ) { _, _ ->
            val name = nameField.text.toString()
            val cost = costField.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(
                    context,
                    "Something went wrong. Check your input values",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                mDatabase.updateProduct(
                    Product(
                        Objects.requireNonNull<Any>(product.id) as Int,
                        name,
                        cost
                    )
                )
                (context as Activity).finish()
                context.startActivity(context.intent)
            }
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { _, _ -> Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show() }
        builder.show()
    }
}