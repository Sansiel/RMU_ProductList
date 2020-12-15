package com.example.mylisttobuy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DialogeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialoge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(R.id.action_DialogeFragment_to_ThirdFragment)
//        val context = getContext()
//        val db = context?.let { DataBaseHandler(it) }
//
//        view.findViewById<Button>(R.id.buttonAddProduct).setOnClickListener {
//            if (editTextProductName.text.toString().isNotEmpty() &&
//                editTextProductCost.text.toString().isNotEmpty()
//            ) {
//                val listToBuy = ListToBuy(editTextProductName.text.toString(), editTextProductCost.text.toString().toInt())
//                if (db != null) {
//                    db.insertData(listToBuy)
//                }
//                findNavController().navigate(R.id.action_DialogeFragment_to_ThirdFragment)
//            }
//            else {
//                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}