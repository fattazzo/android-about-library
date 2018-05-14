/*
 * Project: android-about-library
 * File: AboutButton.kt
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
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import com.gmail.fattazzo.aboutlibrary.R

/**
 * @author fattazzo
 *         <p/>
 *         date: 14/05/18
 */
abstract class AboutButton(protected val mContext: Context) {

    private var text: String = ""

    private var textColor: Int = R.color.aboutlibrary_black

    private var drawable: Drawable? = null

    private var backgroundDark = false

    fun withText(textResId: Int): AboutButton {
        this.text = mContext.getString(textResId)
        return this
    }

    fun withText(text: String): AboutButton {
        this.text = text
        return this
    }

    fun withTextColor(textColorResId: Int): AboutButton {
        this.textColor = textColorResId
        return this
    }

    fun withDrawable(drawableResId: Int): AboutButton {
        this.drawable = mContext.getDrawable(drawableResId)
        return this
    }

    fun withDrawable(drawable: Drawable): AboutButton {
        this.drawable = drawable
        return this
    }

    fun withBackgroundDark(darkBG: Boolean = true): AboutButton {
        this.backgroundDark = darkBG
        return this
    }

    fun create(): View {
        val button = Button(mContext)
        button.text = text
        button.setTextColor(ContextCompat.getColor(mContext, textColor))
        button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

        val drawableBG = if (backgroundDark) R.drawable.aboutlibrary_button_background else R.drawable.aboutlibrary_button_background_white
        button.background = mContext.getDrawable(drawableBG)

        configure(button)

        return button
    }

    abstract fun configure(view: View)
}