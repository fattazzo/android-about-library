/*
 * Project: android-about-library
 * File: AboutFabBuilder.kt
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
import com.gmail.fattazzo.aboutlibrary.view.buttons.AboutFab
import java.io.Serializable

/**
 * @author fattazzo
 *         <p/>
 *         date: 16/05/18
 */
class AboutFabBuilder : Serializable {

    var drawableResId: Int? = null
        private set

    var action: Action? = null
        private set

    var url: String? = null
        private set

    var color: Int = R.color.aboutlibrary_light_gray
        private set

    fun withColor(color: Int): AboutFabBuilder {
        this.color = color
        return this
    }

    fun withUrl(url: String): AboutFabBuilder {
        this.url = url
        return this
    }

    fun withAction(action: Action): AboutFabBuilder {
        this.action = action
        return this
    }

    fun withDrawable(drawableResId: Int): AboutFabBuilder {
        this.drawableResId = drawableResId
        return this
    }

    fun build(context: Context): View = AboutFab(context, this).create()
}