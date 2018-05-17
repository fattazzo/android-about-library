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
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.builder.AboutButtonBuilder
import com.gmail.fattazzo.aboutlibrary.utils.Utils
import java.io.Serializable

/**
 * @author fattazzo
 *         <p/>
 *         date: 14/05/18
 */
class AboutButton(private val mContext: Context, private val builder: AboutButtonBuilder) : Serializable {

    fun create(): View {
        val button = Button(mContext)
        button.text = if (builder.textResId != null) mContext.getString(builder.textResId!!) else builder.text
        button.setTextColor(ContextCompat.getColor(mContext, builder.textColor))

        if (builder.drawableResId != null) {
            button.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, builder.drawableResId!!), null, null, null)
        }

        val drawableBG = if (builder.backgroundDark) R.drawable.aboutlibrary_button_background else R.drawable.aboutlibrary_button_background_white
        button.setBackgroundResource(drawableBG)

        when {
            builder.url != null -> button.setOnClickListener { Utils.openLink(mContext, builder.url) }
            builder.action != null -> button.setOnClickListener({ builder.action!!.run(mContext) })
        }

        return button
    }
}