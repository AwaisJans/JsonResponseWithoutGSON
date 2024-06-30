package com.json.response.without.gson.app.interfaces

import com.json.response.without.gson.app.adapter.JsonContentAdapter


interface OnClickJsonContentItem {

    fun onBindVH(position:Int, holder: JsonContentAdapter.JsonContentVH)

}