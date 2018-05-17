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
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.support.v7.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.builder.AboutButtonBuilder
import com.gmail.fattazzo.aboutlibrary.builder.Action
import com.gmail.fattazzo.aboutlibrary.domain.Project
import com.squareup.picasso.Picasso


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class AppView(private val mContext: Context, private val project: Project, private val lang: String, private val flatStyle: Boolean, private val additionalAppButtons: List<AboutButtonBuilder>) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_app, null)

    fun create(): View {
        val i18n = project.getI18n(lang) ?: project.getI18n()

        mRootView.findViewById<TextView>(R.id.appTitleTV).text = i18n?.title.orEmpty()
        mRootView.findViewById<TextView>(R.id.appTitleShadowTV).text = i18n?.title.orEmpty()
        mRootView.findViewById<TextView>(R.id.appTitleShadowTV).paint.strokeWidth = 5f
        mRootView.findViewById<TextView>(R.id.appTitleShadowTV).paint.style = Paint.Style.STROKE

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
            buttons.add(buildUrlButton(R.string.aboutlibrary_play_store, R.drawable.aboutlibrary_googleplay, it, flatStyle))
        }

        project.githubUrl?.let {
            buttons.add(buildUrlButton(R.string.aboutlibrary_github_project, R.drawable.aboutlibrary_github, it, flatStyle))
        }

        project.websiteUrl?.let {
            buttons.add(buildUrlButton(R.string.aboutlibrary_website, R.drawable.aboutlibrary_website, it, flatStyle))
        }

        project.wikiUrl?.let {
            buttons.add(buildUrlButton(R.string.aboutlibrary_wiki, R.drawable.aboutlibrary_wiki, it, flatStyle))
        }

        val rateItButton = buildRateItButton()
        rateItButton?.let { buttons.add(it) }

        additionalAppButtons.forEach { buttons.add(it.build(mContext)) }

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

    private fun buildUrlButton(textResId: Int, drawableResId: Int, url: String, flat: Boolean): View {
        return AboutButtonBuilder()
                .withText(textResId)
                .withDrawable(drawableResId)
                .withUrl(url)
                .withFlatStyle(flat)
                .build(mContext)
    }

    private fun buildRateItButton(): View? {
        return project.playStoreUrl?.let {
            val rateItUrl = "market://details?id=${it.substringAfter("id=")}"

            AboutButtonBuilder()
                    .withText(R.string.aboutlibrary_rate_it)
                    .withDrawable(R.drawable.aboutlibrary_star)
                    .withFlatStyle(flatStyle)
                    .withAction(object : Action {
                        override fun run(context: Context) {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rateItUrl)))
                        }
                    })
                    .build(mContext)
        }
    }
}