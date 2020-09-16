package com.a.amp.core.bindingadapters

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.a.amp.core.resource.Resource
import com.a.amp.core.resource.Status

@BindingAdapter("app:visibleOnResult")
fun View.visibleOnResult(resource: Resource<*>?) {
    isVisible = resource?.status == Status.LOADING
}