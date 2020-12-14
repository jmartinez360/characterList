package com.android.lidlapp.utils

import android.widget.ImageView
import coil.load
import com.android.lidlapp.R

interface ImageLoader {
    fun loadImageIntoView(url: String, imageView: ImageView)
    fun loadTinyImageIntoView(url: String, imageView: ImageView)
}

class CoilImageLoader : ImageLoader {

    override fun loadImageIntoView(url: String, imageView: ImageView) {
        imageView.load(url) {
            crossfade(true)
            error(R.drawable.placeholder)
        }
    }

    override fun loadTinyImageIntoView(url: String, imageView: ImageView) {
        imageView.load(url) {
            crossfade(true)
            error(R.drawable.ic_baseline_image_24)
            placeholder(R.drawable.ic_baseline_image_24)
        }
    }
}