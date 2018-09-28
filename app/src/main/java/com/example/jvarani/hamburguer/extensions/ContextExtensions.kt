package com.example.john.capptan.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.WindowManager
import android.widget.Toast

fun Context.color(@ColorRes colorId: Int): Int {
    return ResourcesCompat.getColor(resources, colorId, null)
}

fun Activity.showToastShort(text : String, isLong: Boolean){
    if (isLong)
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    else
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
}