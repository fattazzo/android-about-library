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
import com.gmail.fattazzo.aboutlibrary.domain.Info
import com.gmail.fattazzo.aboutlibrary.loader.Closure
import com.gmail.fattazzo.aboutlibrary.loader.InfoBuilder
import com.gmail.fattazzo.aboutlibrary.loader.InfoDownloaderTask
import com.gmail.fattazzo.aboutlibrary.view.box.AuthorView
import com.gmail.fattazzo.aboutlibrary.view.box.OtherProjectsView
import com.gmail.fattazzo.aboutlibrary.view.box.app.AppButton
import com.gmail.fattazzo.aboutlibrary.view.box.app.AppView
import java.net.URL


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
open class AboutView(private val mContext: Context) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_about, null)
    private var mErrorView: View = mInflater.inflate(R.layout.aboutlibrary_view_about_error, null)

    private var boxLayout: LinearLayout = mRootView.findViewById(R.id.boxLayout)

    private var infoUrl: String? = null
    private var infoJsonSring: String? = null
    private var info: Info? = null

    private var appBox = true
    private var authorBox = true
    private var otherProjectsBox = true

    private var lang: String = "default"
    private var idApp: String = ""

    private var additionalAppButton = listOf<AppButton>()

    fun withAdditionalAppButton(buttons: List<AppButton>): AboutView {
        this.additionalAppButton = buttons
        return this
    }

    fun withInfoUrl(infoUrl: String): AboutView {
        this.infoUrl = infoUrl
        return this
    }

    fun withInfo(info: Info): AboutView {
        this.info = info
        return this
    }

    fun withInfoJsonSring(infoJsonSring: String): AboutView {
        this.infoJsonSring = infoJsonSring
        return this
    }

    fun withAppId(idApp: String): AboutView {
        this.idApp = idApp
        return this
    }

    fun withLang(lang: String = "default"): AboutView {
        this.lang = lang
        return this
    }

    fun withAppBox(appBox: Boolean): AboutView {
        this.appBox = appBox
        return this
    }

    fun withAuthorBox(authorBox: Boolean): AboutView {
        this.authorBox = authorBox
        return this
    }

    fun withOtherProjectsBox(otherProjectsBox: Boolean): AboutView {
        this.otherProjectsBox = otherProjectsBox
        return this
    }

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
        this.info = info
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
        return when {
            info != null -> create(info!!)
            !infoJsonSring.isNullOrBlank() -> create(infoJsonSring!!)
            !infoUrl.isNullOrBlank() -> create(URL(infoUrl))
            else -> mErrorView
        }
    }

    private fun bindView() {
        boxLayout.removeAllViews()
        if (info != null) {
            buildAppBox()
            buildAuthorBox()
            buildOtherProjectsBox()
        } else {
            boxLayout.addView(mErrorView)
        }
    }

    private fun buildAppBox() {
        val app = info?.getProjectById(idApp)
        if (appBox && app != null) {
            val appBoxView = AppView(mContext, app, lang, additionalAppButton).create()
            boxLayout.addView(appBoxView)
        }
    }

    private fun buildAuthorBox() {
        if (authorBox) {
            val authorBoxView = AuthorView(mContext, info?.author).create()
            boxLayout.addView(authorBoxView)
        }
    }

    private fun buildOtherProjectsBox() {
        if (otherProjectsBox) {
            val otherProjectsBoxView = OtherProjectsView(mContext, info?.projects.orEmpty(), lang).create()
            boxLayout.addView(otherProjectsBoxView)
        }
    }

    private fun loadInfo(json: String) {
        val loadingLayout = mRootView.findViewById<ConstraintLayout>(R.id.loadingLayout)
        loadingLayout.visibility = View.VISIBLE

        info = try {
            InfoBuilder.build(json)
        } catch (e: Exception) {
            null
        } finally {
            loadingLayout.visibility = View.GONE
        }
        bindView()
    }

    private fun loadInfo(url: URL) {
        val loadingLayout = mRootView.findViewById<ConstraintLayout>(R.id.loadingLayout)
        loadingLayout.visibility = View.VISIBLE

        InfoDownloaderTask(object : Closure<Info?> {
            override fun onPostExecute(result: Info?) {
                info = result
                loadingLayout.visibility = View.GONE
                bindView()
            }

            override fun onCancel() {
                info = null
                loadingLayout.visibility = View.GONE
                bindView()
            }
        }).execute(url)
    }
}