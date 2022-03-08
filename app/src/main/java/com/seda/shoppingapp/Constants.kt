package com.seda.shoppingapp

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment

object Constants {
    const val MALE :String="male"
    const val FEMALE :String="female"
const val MOBILE:String="mobile"
    const val GENDER:String="gender"
    const val USER_PROFILE:String="User_profile_mage"
    const val IMAGE :String ="image"

    fun getMimeType(activity:Fragment,path: Uri?): String? {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.requireActivity().contentResolver.getType(path!!))


    }

}