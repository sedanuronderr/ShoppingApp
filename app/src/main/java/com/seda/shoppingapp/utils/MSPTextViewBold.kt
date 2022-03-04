package com.seda.shoppingapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class MSPTextViewBold(context:Context,attrs:AttributeSet):AppCompatTextView(context,attrs){
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface = Typeface.createFromAsset(context.assets,"Coming.ttf")
         setTypeface(typeface)
    }
}