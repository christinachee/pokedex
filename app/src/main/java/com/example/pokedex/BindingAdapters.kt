package com.example.pokedex

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, id: Int) {
    id.let {
        Picasso.get()
            .load("https://pokeres.bastionbot.org/images/pokemon/$it.png")
            .into(imageView)
    }
}