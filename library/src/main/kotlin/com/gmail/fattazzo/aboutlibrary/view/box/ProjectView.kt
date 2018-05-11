/*
 * Project: android-about-library
 * File: ProjectView.kt
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

package com.gmail.fattazzo.aboutlibrary.view.box

import android.content.Context
import android.support.v7.widget.GridLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.domain.Project
import com.gmail.fattazzo.aboutlibrary.utils.Utils
import com.squareup.picasso.Picasso

/**
 * @author fattazzo
 *         <p/>
 *         date: 11/05/18
 */
class ProjectView(private val mContext: Context, private val project: Project, private val lang: String) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_project, null)

    fun create(): View {
        val i18n = project.getI18n(lang) ?: project.getI18n()

        val iconImageView = mRootView.findViewById<ImageView>(R.id.iconImageView)
        if (project.icon.isNotBlank())
            Picasso.get().load(project.icon).into(iconImageView)

        mRootView.findViewById<TextView>(R.id.titleTV).text = i18n?.title
        mRootView.findViewById<TextView>(R.id.descriptionTV).text = i18n?.description

        val projectsLayout = mRootView.findViewById<GridLayout>(R.id.projectsLayout)
        val linkViews = buildProjectLinks(project)

        val columns = mContext.resources.getInteger(R.integer.aboutlibrary_app_button_columns)
        val row = linkViews.size / columns
        projectsLayout.alignmentMode = GridLayout.ALIGN_BOUNDS
        projectsLayout.columnCount = columns
        projectsLayout.rowCount = row + 1

        var i = 0
        var column = 0
        var r = 0
        while (i < linkViews.size) {
            if (column == columns) {
                column = 0
                r++
            }
            val button = linkViews[i]
            projectsLayout.addView(button, i)

            val param = GridLayout.LayoutParams()
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            param.setMargins(4, 2, 4, 2)
            button.layoutParams = param
            i++
            column++
        }

        return mRootView
    }

    private fun buildProjectLinks(project: Project): List<View> {
        val projects = mutableListOf<View>()

        project.playStoreUrl?.let {
            projects.add(buildButton(R.string.aboutlibrary_play_store, R.color.aboutlibrary_playstore, R.drawable.aboutlibrary_googleplay, it))
        }
        project.githubUrl?.let {
            projects.add(buildButton(R.string.aboutlibrary_github_project, R.color.aboutlibrary_black, R.drawable.aboutlibrary_github, it))
        }
        project.appleStoreUrl?.let {
            projects.add(buildButton(R.string.aboutlibrary_apple_store, R.color.aboutlibrary_black, R.drawable.aboutlibrary_apple, it))
        }
        project.websiteUrl?.let {
            projects.add(buildButton(R.string.aboutlibrary_website, R.color.aboutlibrary_website, R.drawable.aboutlibrary_website, it))
        }

        return projects
    }

    private fun buildButton(textResId: Int, textColor: Int, drawableResId: Int, urlToOpen: String?): Button {
        val button = Button(mContext)
        button.text = mContext.getString(textResId)
        button.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(drawableResId), null, null, null)
        button.setTextColor(textColor)
        button.background = mContext.getDrawable(R.drawable.aboutlibrary_button_background)
        button.setOnClickListener { Utils.openLink(mContext, urlToOpen) }
        return button
    }
}