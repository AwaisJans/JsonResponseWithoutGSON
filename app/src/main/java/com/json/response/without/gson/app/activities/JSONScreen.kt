package com.json.response.without.gson.app.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.json.response.without.gson.app.R
import com.json.response.without.gson.app.adapter.JsonContentAdapter
import com.json.response.without.gson.app.databinding.ActivityJsonscreenBinding
import com.json.response.without.gson.app.interfaces.OnClickJsonContentItem
import com.json.response.without.gson.app.model.Todo
import com.json.response.without.gson.app.utils.Config.Companion.BASE_URL
import com.json.response.without.gson.app.utils.Config.Companion.COMPLETED
import com.json.response.without.gson.app.utils.Config.Companion.END_POINTS
import com.json.response.without.gson.app.utils.Config.Companion.PENDING
import com.json.response.without.gson.app.utils.gson.Gson
import com.json.response.without.gson.app.utils.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JSONScreen : AppCompatActivity(), OnClickJsonContentItem {

    private lateinit var b: ActivityJsonscreenBinding

    private var listTodo: List<Todo>? = null
    private var adapter: JsonContentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityJsonscreenBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.ivBack.setOnClickListener {
            finish()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            fetchTodos()
        },1000)

    }


    private fun fetchTodos(){
        val url = URL("$BASE_URL/$END_POINTS")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        // Add headers
//        connection.setRequestProperty("Authorization", "Bearer YOUR_AUTH_TOKEN")
//        connection.setRequestProperty("Accept", "application/json")


        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // handle response
           handleResponse(connection.inputStream)
        } else {
            b.pBar.visibility = View.GONE
            b.tvLoader.text = responseCode.toString()
        }
    }

    private fun handleResponse(inputStream: InputStream?) {
        // getting list from json
        val response = BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
        val gson = Gson()
        val listType = object : TypeToken<List<Todo>>() {}.type
        listTodo = gson.fromJson(response, listType)


        // setup RV
        val rv = b.rvJsonContent
        adapter = JsonContentAdapter(listTodo!!, this@JSONScreen)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this@JSONScreen)
        rv.setHasFixedSize(true)
        b.idLoader.visibility = View.GONE

    }


    override fun onBindVH(position: Int, holder: JsonContentAdapter.JsonContentVH) {
        val item = listTodo!![position]
        holder.textView.text = item.title
        val pos = position + 1
        holder.positionTV.text = pos.toString()

        if (item.completed!!) {
            holder.booleanTV.text = COMPLETED
            holder.checkBox.setImageResource(R.drawable.ic_checked)
        } else {
            holder.booleanTV.text = PENDING
            holder.checkBox.setImageResource(R.drawable.cross_circle_svgrepo_com)
        }


    }


}