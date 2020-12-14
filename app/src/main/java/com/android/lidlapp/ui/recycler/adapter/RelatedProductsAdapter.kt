package com.android.lidlapp.ui.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.lidlapp.R
import com.android.lidlapp.model.ProductModel
import com.android.lidlapp.ui.recycler.viewholder.BaseViewHolder
import com.android.lidlapp.ui.recycler.viewholder.RelatedProductViewHolder

class RelatedProductsAdapter : RecyclerView.Adapter<BaseViewHolder<ProductModel>>() {

    private var products = mutableListOf<ProductModel>()

    override fun getItemCount() = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ProductModel> {
        return RelatedProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.related_product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ProductModel>, position: Int) {
        holder.bindItem(products[position])
    }

    fun addProducts(newProducts: List<ProductModel>) {
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}