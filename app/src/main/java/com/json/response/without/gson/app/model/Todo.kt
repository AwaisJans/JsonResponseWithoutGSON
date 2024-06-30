package com.json.response.without.gson.app.model

import com.google.gson.annotations.SerializedName
import com.json.response.without.gson.app.utils.Config.Companion.ID
import com.json.response.without.gson.app.utils.Config.Companion.IS_COMPLETED
import com.json.response.without.gson.app.utils.Config.Companion.TITLE
import com.json.response.without.gson.app.utils.Config.Companion.USER_ID

data class Todo(
    @SerializedName(USER_ID) val userId: Int? = 0,
    @SerializedName(ID) val id: Int? = 0,
    @SerializedName(TITLE) val title: String? = "",
    @SerializedName(IS_COMPLETED) val completed: Boolean? = false
)