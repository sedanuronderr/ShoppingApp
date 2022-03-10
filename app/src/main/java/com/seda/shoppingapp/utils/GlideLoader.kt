package com.seda.shoppingapp.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.seda.shoppingapp.R
import java.io.IOException
import java.net.URI

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageURI: Any, imageView: ImageView){
         try {
             Glide
                 .with(context)
                 .load(Uri.parse(imageURI.toString()))
                 .centerCrop()
                 .placeholder(R.drawable.user_background)
                 .into(imageView)
         }catch (e:IOException){
             e.printStackTrace()
         }
    }


}