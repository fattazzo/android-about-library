/*
 * Project: android-about-library
 * File: AboutView.kt
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

package com.gmail.fattazzo.aboutlibrary.view

import android.content.Context
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.builder.AboutViewBuilder
import com.gmail.fattazzo.aboutlibrary.domain.Info
import com.gmail.fattazzo.aboutlibrary.loader.Closure
import com.gmail.fattazzo.aboutlibrary.loader.InfoBuilder
import com.gmail.fattazzo.aboutlibrary.loader.InfoDownloaderTask
import com.gmail.fattazzo.aboutlibrary.view.box.app.AppView
import com.gmail.fattazzo.aboutlibrary.view.box.author.AuthorView
import com.gmail.fattazzo.aboutlibrary.view.box.projects.OtherProjectsView
import java.io.Serializable
import java.net.URL


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class AboutView(private val mContext: Context, private val builder: AboutViewBuilder) : Serializable {

    private lateinit var mInflater: LayoutInflater
    private lateinit var mRootView: View
    private lateinit var mErrorView: View

    private lateinit var boxLayout: LinearLayout
    private var boxLayout2: LinearLayout? = null

    /**
     * Assemble and build the view based on the configured parameters.
     *
     * @param url url of configuration
     * @return the AboutView
     *
     */
    private fun create(url: URL): View {
        loadInfo(url)
        return mRootView
    }

    /**
     * Assemble and build the view based on the configured parameters.
     *
     * @param json json string of configuration
     * @return the AboutView
     *
     */
    private fun create(json: String): View {
        loadInfo(json)
        return mRootView
    }

    /**
     * Assemble and build the view based on the configured parameters.
     *
     * @param info info configuration
     * @return the AboutView
     *
     */
    private fun create(info: Info): View {
        this.builder.withInfo(info)
        Handler().post({ bindView() })

        return mRootView
    }

    /**
     * Assemble and build the view based on the configured parameters.
     *
     * @return the AboutView
     *
     */
    fun create(): View {
        return try {
            mInflater = LayoutInflater.from(mContext)
            mRootView = mInflater.inflate(R.layout.aboutlibrary_view_about, null)
            mErrorView = mInflater.inflate(R.layout.aboutlibrary_view_about_error, null)

            boxLayout = mRootView.findViewById(R.id.boxLayout)
            boxLayout2 = mRootView.findViewById(R.id.boxLayout2)

            return when {
                builder.info != null -> create(builder.info!!)
                !builder.infoJsonSring.isNullOrBlank() -> create(builder.infoJsonSring!!)
                !builder.infoUrl.isNullOrBlank() -> create(URL(builder.infoUrl))
                else -> mErrorView
            }
        } catch (e: Exception) {
            mErrorView
        }
    }

    private fun bindView() {
        boxLayout.removeAllViews()
        if (builder.info != null) {
            buildAppBox()
            buildAuthorBox()
            buildOtherProjectsBox()
        } else {
            boxLayout.addView(mErrorView)
        }
    }

    private fun buildAppBox() {
        val app = builder.info?.getProjectById(builder.idApp)
        if (builder.appBox && app != null) {
            val appBoxView = AppView(mContext, app, builder.lang, builder.additionalAppButtons).create()
            boxLayout.addView(appBoxView)
        }
    }

    private fun buildAuthorBox() {
        if (builder.authorBox) {
            val authorBoxView = AuthorView(mContext, builder.info?.author, builder.additionalAuthorButtons).create()
            boxLayout.addView(authorBoxView)
        }
    }

    private fun buildOtherProjectsBox() {
        if (builder.otherProjectsBox) {

            var projects = builder.info?.projects.orEmpty()
            if (builder.excludeThisAppFromProjects) {
                projects = projects.filter { it.id != builder.idApp }
            }

            val otherProjectsBoxView = OtherProjectsView(mContext, projects, builder.lang, builder.additionalProjectButtons.toMap()).create()
            if (boxLayout2 != null) {
                boxLayout2!!.addView(otherProjectsBoxView)
            } else
                boxLayout.addView(otherProjectsBoxView)
        }
    }

    private fun loadInfo(json: String) {
        val loadingLayout = mRootView.findViewById<ConstraintLayout>(R.id.loadingLayout)
        loadingLayout.visibility = View.VISIBLE

        val info: Info? = try {
            InfoBuilder.build(json)
        } catch (e: Exception) {
            null
        } finally {
            loadingLayout.visibility = View.GONE
        }
        builder.withInfo(info)
        bindView()
    }

    private fun loadInfo(url: URL) {
        val loadingLayout = mRootView.findViewById<ConstraintLayout>(R.id.loadingLayout)
        loadingLayout.visibility = View.VISIBLE

        InfoDownloaderTask(object : Closure<Info?> {
            override fun onPostExecute(result: Info?) {
                builder.withInfo(result)
                loadingLayout.visibility = View.GONE
                bindView()
            }

            override fun onCancel() {
                builder.withInfo(null)
                loadingLayout.visibility = View.GONE
                bindView()
            }
        }).execute(url)
    }
}