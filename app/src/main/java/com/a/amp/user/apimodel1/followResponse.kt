package com.a.amp.user.apimodel1


import com.a.amp.article.apimodel2.Author
import com.google.gson.annotations.SerializedName

data class followResponse(
    @SerializedName("profile")
    val profile: Author
)