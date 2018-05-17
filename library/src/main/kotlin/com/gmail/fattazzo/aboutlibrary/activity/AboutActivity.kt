/*
 * Project: android-about-library
 * File: AboutActivity.kt
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

package com.gmail.fattazzo.aboutlibrary.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.builder.AboutViewBuilder

class AboutActivity : AppCompatActivity() {

    private var aboutViewBuilder: AboutViewBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState?.getSerializable(builderSave) != null) {
            aboutViewBuilder = savedInstanceState.getSerializable(builderSave) as AboutViewBuilder
        } else {
            if (intent.getSerializableExtra(EXTRA_BUILDER) != null) {
                aboutViewBuilder = intent.getSerializableExtra(EXTRA_BUILDER) as AboutViewBuilder?
            }
        }

        if (aboutViewBuilder != null) {
            setContentView(aboutViewBuilder!!.build(this))
        } else {
            setContentView(R.layout.aboutlibrary_view_about_error)
        }
    }

    public override fun onSaveInstanceState(bundle_: Bundle) {
        super.onSaveInstanceState(bundle_)
        bundle_.putSerializable(builderSave, aboutViewBuilder)
    }

    companion object {

        private const val builderSave = "builderSave"

        const val EXTRA_BUILDER = "extraBuilder"
    }
}
