/*
 * Project: android-about-library
 * File: AppView.kt
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
import android.support.v7.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.domain.Project
import com.gmail.fattazzo.aboutlibrary.view.buttons.AboutButton
import com.gmail.fattazzo.aboutlibrary.view.buttons.AboutUrlButton
import com.squareup.picasso.Picasso


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class AppView(private val mContext: Context, private val project: Project, private val lang: String, private val additionalAppButtons: List<AboutButton>) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_app, null)

    fun create(): View {
        val i18n = project.getI18n(lang) ?: project.getI18n()

        val titleTV = mRootView.findViewById<TextView>(R.id.appTitleTV)
        titleTV.text = i18n?.title.orEmpty()

        val appIconView = mRootView.findViewById<ImageView>(R.id.appIconView)
        if (project.icon.isNotBlank())
            Picasso.get().load(project.icon).into(appIconView)

        buildButtonLayout(project)

        return mRootView
    }

    private fun buildButtonLayout(project: Project) {
        val buttonLayout: GridLayout = mRootView.findViewById(R.id.buttonLayout)

        val buttons = mutableListOf<View>()
        project.playStoreUrl?.let {
            buttons.add(AboutUrlButton(mContext, it)
                    .withText(R.string.aboutlibrary_play_store)
                    .withDrawable(R.drawable.aboutlibrary_googleplay)
                    .create())
        }

        project.githubUrl?.let {
            buttons.add(AboutUrlButton(mContext, it)
                    .withText(R.string.aboutlibrary_github_project)
                    .withDrawable(R.drawable.aboutlibrary_github)
                    .create())
        }

        project.websiteUrl?.let {
            buttons.add(AboutUrlButton(mContext, it)
                    .withText(R.string.aboutlibrary_website)
                    .withDrawable(R.drawable.aboutlibrary_website)
                    .create())
        }

        project.wikiUrl?.let {
            buttons.add(AboutUrlButton(mContext, it)
                    .withText(R.string.aboutlibrary_wiki)
                    .withDrawable(R.drawable.aboutlibrary_wiki)
                    .create())
        }

        additionalAppButtons.forEach { buttons.add(it.create()) }

        val columns = mContext.resources.getInteger(R.integer.aboutlibrary_app_button_columns)
        val row = buttons.size / columns
        buttonLayout.alignmentMode = GridLayout.ALIGN_BOUNDS
        buttonLayout.columnCount = columns
        buttonLayout.rowCount = row + 1

        var i = 0
        var column = 0
        var r = 0
        while (i < buttons.size) {
            if (column == columns) {
                column = 0
                r++
            }
            val button = buttons[i]
            buttonLayout.addView(button, i)

            val param = GridLayout.LayoutParams()
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            param.setMargins(4, 2, 4, 2)
            button.layoutParams = param
            i++
            column++
        }
    }
}