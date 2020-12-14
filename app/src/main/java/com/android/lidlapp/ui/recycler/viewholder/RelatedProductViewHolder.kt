package com.android.lidlapp.ui.recycler.viewholder

import android.view.View
import com.android.lidlapp.databinding.RelatedProductItemBinding
import com.android.lidlapp.model.ProductModel
import com.android.lidlapp.utils.ImageLoader
import org.koin.core.KoinComponent
import org.koin.core.inject

class RelatedProductViewHolder constructor(itemView: View) :
    BaseViewHolder<ProductModel>(itemView), KoinComponent {

    private val imageLoader: ImageLoader by inject()

    private val binding = RelatedProductItemBinding.bind(itemView)

    override fun bindItem(item: ProductModel) {
        binding.productName.text = item.productName
        imageLoader.loadTinyImageIntoView(item.image, binding.productImage)
    }
}