package com.a.amp.user.data

data class WritingCvDataItem(
    var title: String,
    var text: String,
    var name: String,
    var days: String,
    var id: String,
    var isTag: Boolean,
    var isFav: Boolean,
    var FavCont: Int = 0
)