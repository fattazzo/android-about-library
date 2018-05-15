/*
 * Project: android-about-library
 * File: AboutFab.kt
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

package com.gmail.fattazzo.aboutlibrary.view.buttons

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.utils.Utils


/**
 * @author fattazzo
 *         <p/>
 *         date: 14/05/18
 */
class AboutFab(private val mContext: Context) {

    private var drawable: Drawable? = null

    private var action: View.OnClickListener? = null

    private var color: Int = R.color.aboutlibrary_light_gray

    fun withColor(color: Int): AboutFab {
        this.color = color
        return this
    }

    fun withUrl(url: String): AboutFab {
        this.action = View.OnClickListener { Utils.openLink(mContext, url) }
        return this
    }

    fun withAction(action: View.OnClickListener): AboutFab {
        this.action = action
        return this
    }

    fun withDrawable(drawableResId: Int): AboutFab {
        this.drawable = mContext.getDrawable(drawableResId)
        return this
    }

    fun withDrawable(drawable: Drawable): AboutFab {
        this.drawable = drawable
        return this
    }

    fun create(): View {
        val fab = FloatingActionButton(mContext)
        fab.setImageDrawable(drawable)
        fab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(mContext, color))
        fab.setOnClickListener(action)
        fab.size = FloatingActionButton.SIZE_MINI

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        params.setMargins(8, 0, 0, 8)
        fab.layoutParams = params
        return fab
    }
}