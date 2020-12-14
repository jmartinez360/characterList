package com.android.lidlapp.ui.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T> constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindItem(item: T)

    open fun bindItemWithPayload(item: T, payload: MutableList<Any>) {}
}