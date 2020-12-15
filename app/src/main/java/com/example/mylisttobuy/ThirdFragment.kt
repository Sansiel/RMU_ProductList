package com.example.mylisttobuy

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {
    private lateinit var dataBase: SqliteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactView: RecyclerView = view.findViewById<RecyclerView>(R.id.myProductList)

        val linearLayoutManager = LinearLayoutManager(context)
        contactView.layoutManager = linearLayoutManager
        contactView.setHasFixedSize(true)
        dataBase = SqliteDatabase(context)
        var allProduct = dataBase.listProduct()
        if (allProduct.size > 0) {
            val mAdapter = context?.let { ProductAdapter(it, allProduct) }
            contactView.adapter = mAdapter
        }
        else {
            Toast.makeText(
                context,
                "There is no product in the database. Start adding now",
                Toast.LENGTH_LONG
            ).show()
        }



        view.findViewById<Button>(R.id.buttonReturn).setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener {
//            findNavController().navigate(R.id.action_ThirdFragment_to_DialogeFragment)
            addTaskDialog()
            findNavController().navigate(R.id.action_ThirdFragment_to_ThirdFragment)
        }
    }
    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(context)
        val subView = inflater.inflate(R.layout.add_product, null)
        val nameField: EditText = subView.findViewById(R.id.enterName)
        val noField: EditText = subView.findViewById(R.id.enterCost)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Add new PRODUCT")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton("ADD PRODUCT") { _, _ ->
            val name = nameField.text.toString()
            val cost = noField.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(
                    context,
                    "Something went wrong. Check your input values",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                val newProduct = Product(name, cost)
                dataBase.addProduct(newProduct)
//                finish()
//                startActivity(intent)
            }
        }
        builder.setNegativeButton("CANCEL") { _, _ -> Toast.makeText(context, "Task cancelled",
            Toast.LENGTH_LONG).show()}
        builder.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        dataBase.close()
    }
}