/*
 * Project: android-about-library
 * File: Utils.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.aboutlibrary.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
object Utils {

    /**
     * Open link in external activity.
     *
     * @param link link to open
     */
    fun openLink(context: Context, link: String?) {
        link?.let {
            val i = Intent(Intent.ACTION_VIEW)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.data = Uri.parse(it)
            context.startActivity(i)
        }
    }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val width = if (!drawable.bounds.isEmpty)
            drawable.bounds.width()
        else
            drawable.intrinsicWidth

        val height = if (!drawable.bounds.isEmpty)
            drawable.bounds.height()
        else
            drawable.intrinsicHeight

        // Now we check we are > 0
        val bitmap = Bitmap.createBitmap(if (width <= 0) 1 else width, if (height <= 0) 1 else height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun getDominantColor(drawable: Drawable?): Int {
        val bitmap = drawableToBitmap(drawable) ?: return Color.BLACK

        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()

        val red = Color.red(color)
        val blue = Color.blue(color)
        val green = Color.green(color)
        return Color.argb(255, red, green, blue)
    }
}