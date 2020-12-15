package com.example.mylisttobuy

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

private var email: String = "";
private var pass: String = "";

class FirstFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {

            val mt = MyTask()
            mt.execute()
            Handler().postDelayed(Runnable {
                val emailText =  view.findViewById<EditText>(R.id.editTextTextEmailAddress).getText().toString()
                val passText = view.findViewById<EditText>(R.id.editTextTextPassword).getText().toString()

                if ((email == emailText) and (pass == passText)) {
                    findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
                }
                else {
                    Toast.makeText(
                        context,
                        "Неверный логин или пароль",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, 1000)

//            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
    }


    class MyTask() : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            var doc: Document? = null //Здесь хранится будет разобранный html документ
            try {

                doc =
                    Jsoup.connect("http://is.ulstu.ru/")
                        .timeout(60000).userAgent("Chrome/81.0.4044.138").ignoreHttpErrors(true)
                        .get();
            } catch (e: IOException) {
                e.printStackTrace()
                email = e.toString()
            }

            if (doc != null) email = doc.select("body > div.container.body-content > div.left-panel > div.login-panel > form > div > div:nth-child(1) > label").text();
//            else email = "Ошибка";

            if (doc != null) pass = doc.select("body > div.container.body-content > div.left-panel > div.login-panel > form > div > div:nth-child(2) > label").text();
            else pass = "Ошибка";

            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
//            log = login;
        }
    }
}