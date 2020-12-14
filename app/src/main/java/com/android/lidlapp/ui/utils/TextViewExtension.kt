package com.android.lidlapp.ui.utils

import android.widget.TextView
import com.android.lidlapp.R
import com.android.lidlapp.model.DiscountModel

fun TextView.setTicketDiscountText(textToShow: String? = null, discountType: Int) {
    val color = context.getColor(getDiscountTextColorResource(discountType))
    setTextColor(color)
    textToShow?.let { text = it }
}

private fun getDiscountTextColorResource(discountType: Int): Int {
    return if (discountType == DiscountModel.LOW_DISCOUNT) {
        R.color.black
    } else {
        R.color.white
    }
}