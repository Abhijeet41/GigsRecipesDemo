package com.abhi41.recipesapp.bindingAdapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.abhi41.rrecipesapp.R
import org.jsoup.Jsoup

class RecipesRowBinding {

    companion object{
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            //we used coil image loading library
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }

}