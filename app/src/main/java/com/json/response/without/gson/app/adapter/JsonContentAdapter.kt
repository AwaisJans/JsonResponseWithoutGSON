package com.json.response.without.gson.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.json.response.without.gson.app.R
import com.json.response.without.gson.app.interfaces.OnClickJsonContentItem
import com.json.response.without.gson.app.model.Todo

class JsonContentAdapter (private val mData: List<Todo>, private val mListener: OnClickJsonContentItem) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return JsonContentVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as JsonContentVH
        mListener.onBindVH(position,holder)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class JsonContentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.tvTitle)
        var positionTV: TextView = itemView.findViewById(R.id.positionTV)
        var booleanTV: TextView = itemView.findViewById(R.id.tvBoolean)
        var checkBox: ImageView = itemView.findViewById(R.id.checkboxImageView)
    }






}



