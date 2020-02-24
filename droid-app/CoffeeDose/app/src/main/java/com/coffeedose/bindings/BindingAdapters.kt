package com.coffeedose.bindings

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.coffeedose.R
import com.coffeedose.extensions.setBooleanVisibility

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        //val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .centerInside()
            .into(imgView)
    }
}

/*@BindingAdapter("isVisible")
fun popularityIcon(view: View, visible: Boolean) {
    view.setBooleanVisibility(visible)
}*/

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}