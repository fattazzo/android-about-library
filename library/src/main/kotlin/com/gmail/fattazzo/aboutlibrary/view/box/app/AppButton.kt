/*
 * Project: android-about-library
 * File: AppButton.kt
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

package com.gmail.fattazzo.aboutlibrary.view.box.app

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import com.gmail.fattazzo.aboutlibrary.R

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/05/18
 */
class AppButton(private val mContext: Context) {

    private var text: String = ""

    private var drawable: Drawable? = null

    private var action: View.OnClickListener? = null

    fun withText(textResId: Int): AppButton {
        this.text = mContext.getString(textResId)
        return this
    }

    fun withText(text: String): AppButton {
        this.text = text
        return this
    }

    fun withDrawableResId(drawableResId: Int): AppButton {
        this.drawable = mContext.getDrawable(drawableResId)
        return this
    }

    fun withDrawable(drawable: Drawable): AppButton {
        this.drawable = drawable
        return this
    }

    fun withAction(onClickListener: View.OnClickListener): AppButton {
        this.action = action
        return this
    }

    fun create(): View {
        val button = Button(mContext)
        button.text = text
        button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        button.background = mContext.getDrawable(R.drawable.button_background_white)
        action?.let {
            button.setOnClickListener { it }
        }
        return button
    }
}