package com.seda.shoppingapp.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRadioButton

class MSPRadioButton (context: Context, attrs: AttributeSet): AppCompatRadioButton(context,attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface: Typeface = Typeface.createFromAsset(context.assets,"Coming.ttf")
        setTypeface(typeface)
    }

}