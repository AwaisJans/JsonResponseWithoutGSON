package com.json.response.without.gson.app.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.json.response.without.gson.app.R
import com.json.response.without.gson.app.adapter.JsonContentAdapter
import com.json.response.without.gson.app.adapter.OnClickJsonContentItem
import com.json.response.without.gson.app.databinding.ActivityJsonscreenBinding
import com.json.response.without.gson.app.interfaces.ApiService
import com.json.response.without.gson.app.model.Todo
import com.json.response.without.gson.app.utils.Config.Companion.COMPLETED
import com.json.response.without.gson.app.utils.Config.Companion.PENDING
import com.json.response.without.gson.app.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JSONScreen : AppCompatActivity(),OnClickJsonContentItem {

    private lateinit var b: ActivityJsonscreenBinding

    private var listTodo: MutableList<Todo>? = null
    private var adapter: JsonContentAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityJsonscreenBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.ivBack.setOnClickListener {
            finish()
        }

        fetchTodos()

    }


    private fun fetchTodos() {
        // initialize list
        listTodo = ArrayList()
        val instance = RetrofitClient.instance.create(ApiService::class.java)

        // load data using retrofit
        instance.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                // do something with response
                handleResponse(response)
            }

            // handle error response
            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                b.pBar.visibility = View.GONE
                b.tvLoader.text = t.message.toString()
            }
        })
    }

    private fun handleResponse(response: Response<List<Todo>>) {
        // update ui based on conditions
        if (response.isSuccessful) {
            listTodo = response.body() as MutableList<Todo>
            b.idLoader.visibility = View.GONE

            val rv = b.rvJsonContent
            adapter = JsonContentAdapter(listTodo!!, this@JSONScreen)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(this@JSONScreen)
            rv.setHasFixedSize(true)


        } else {
            b.pBar.visibility = View.GONE
            b.tvLoader.text = response.code().toString()
        }
    }


    override fun onBindVH(position: Int, holder: JsonContentAdapter.JsonContentVH) {
        val item = listTodo!![position]
        holder.textView.text = item.title
        val pos = position + 1
        holder.positionTV.text = pos.toString()

        if(item.completed!!){
            holder.booleanTV.text = COMPLETED
            holder.checkBox.setImageResource(R.drawable.ic_checked)
        }else{
            holder.booleanTV.text = PENDING
            holder.checkBox.setImageResource(R.drawable.cross_circle_svgrepo_com)
        }


    }







}