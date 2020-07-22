package com.example.week6

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_network.*
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        NetworkTask(
            recycler_person,
            LayoutInflater.from(this@NetworkActivity),
            this@NetworkActivity
        ).execute()

    }
}

class NetworkTask(
    val recyclerView: RecyclerView,
    val intlater: LayoutInflater,

):AsyncTask<Any?, Any?, Array<PersonFromServer>>(){
    override fun onPostExecute(result: Array<PersonFromServer>?) {
        //여기는 UI 쓰레드에 접근 가능하다.
        val adapter = PersonAdapter(result!!, inflater)
        recyclerView.adapter = adapter
        super.onPostExecute(result)
    }


    override fun doInBackground(vararg params: Any?): PersonFromServer? {
        val urlString: String = "http://mellowcode.org/json/student"
        val url: URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("content-Type", "application/json")
        Log.d("connn", "111")

        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.d("connn", "inputstream: " + connection.inputStream)
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()

        }
        val data = Gson().fromJson(buffer, PersonFromServer::class.java)


//        val id= buffer.get(7)
//        Log.d("conn","inputstream : "+ temp)

        return data
    }
}

 class PersonAdapter(
    val personList: Array<PersonFromServer>,
    val inflater:LayoutInflater
): RecyclerView.Adapter<PersonAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name: TextView
        val age: TextView
        val intro:TextView

        init {
            name = itemView.findViewById(R.id.person_name)
            age = itemView.findViewById(R.id.person_age)
            intro=itemView.findViewById(R.id.person_ment)
        }
    }

     override fun getItemCount(): Int {
         return personList.size
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=inflater.inflate(R.layout.person_list_item,parent, false)
         return ViewHolder(view)
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.name.setText(personList.get(position).name ?:"")
         holder.age.setText(personList.get(position).age.toString())
         holder.intro.setText(personList.get(position).intro ?:"")
     }





 }