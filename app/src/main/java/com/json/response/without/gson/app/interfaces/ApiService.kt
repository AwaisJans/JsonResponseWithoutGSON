package com.json.response.without.gson.app.interfaces

import com.json.response.without.gson.app.model.Todo
import com.json.response.without.gson.app.utils.Config.Companion.END_POINTS
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINTS)
    fun getTodos(): Call<List<Todo>>
}