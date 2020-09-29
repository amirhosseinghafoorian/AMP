package com.a.amp.home.apimodel


import com.google.gson.annotations.SerializedName

data class tagModel(
    @SerializedName("tags")
    val tags: List<String>
)