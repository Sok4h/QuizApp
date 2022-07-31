package com.sokah.quizapp.model

import android.graphics.drawable.Drawable

data class Category(

    val img : Drawable,
    val name :String,
    val value :String,
    val active :Boolean
)