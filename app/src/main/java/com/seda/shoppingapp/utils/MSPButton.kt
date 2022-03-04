package com.seda.shoppingapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton


class MSPButton (context: Context, attrs: AttributeSet): AppCompatButton(context,attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface = Typeface.createFromAsset(context.assets,"Coming.ttf")
        setTypeface(typeface)
    }

}