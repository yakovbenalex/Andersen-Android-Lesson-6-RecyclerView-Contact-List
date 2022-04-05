package ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.opalevapps.andersenandroidlesson6recyclerviewcontactlist.R

class ImageLoading {
    companion object{
        // load image from url by Picasso library
        fun loadPicturePicasso(context: Context, imageView: ImageView, imageUri: String, imageSize: Int) {
            Picasso.Builder(context)
                .listener { _, _, exception ->
                    Log.e("DownloadImageTaskError", exception.toString())
                }
                .build()
                .load(imageUri + imageSize.toString())
                .fit()
                .noFade()
                .error(R.drawable.ic_person)
                .into(imageView)
        }
    }
}