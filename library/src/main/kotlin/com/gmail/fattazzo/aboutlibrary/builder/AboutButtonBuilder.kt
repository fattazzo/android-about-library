/*
 * Project: android-about-library
 * File: AboutButtonBuilder.kt
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

package com.gmail.fattazzo.aboutlibrary.builder

import android.content.Context
import android.view.View
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.view.buttons.AboutButton
import java.io.Serializable

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/05/18
 */
class AboutButtonBuilder : Serializable {

    var text: String = ""
        private set

    var textResId: Int? = null
        private set

    var textColor: Int = R.color.aboutlibrary_black
        private set

    var backgroundDark = false
        private set

    var flatStyle = false
        private set

    var drawableResId: Int? = null
        private set

    var action: Action? = null
        private set

    var url: String? = null
        private set

    fun withFlatStyle(flat: Boolean = false): AboutButtonBuilder {
        this.flatStyle = flat
        return this
    }

    fun withUrl(url: String): AboutButtonBuilder {
        this.url = url
        return this
    }

    fun withAction(action: Action): AboutButtonBuilder {
        this.action = action
        return this
    }

    fun withDrawable(drawableResId: Int): AboutButtonBuilder {
        this.drawableResId = drawableResId
        return this
    }

    fun withText(textResId: Int): AboutButtonBuilder {
        this.textResId = textResId
        return this
    }

    fun withText(text: String): AboutButtonBuilder {
        this.text = text
        return this
    }

    fun withTextColor(textColorResId: Int): AboutButtonBuilder {
        this.textColor = textColorResId
        return this
    }

    fun withBackgroundDark(darkBG: Boolean = true): AboutButtonBuilder {
        this.backgroundDark = darkBG
        return this
    }

    fun build(context: Context): View = AboutButton(context, this).create()
}