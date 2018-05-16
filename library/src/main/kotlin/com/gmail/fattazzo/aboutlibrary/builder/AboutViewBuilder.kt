/*
 * Project: android-about-library
 * File: AboutViewBuilder.kt
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
import com.gmail.fattazzo.aboutlibrary.domain.Info
import com.gmail.fattazzo.aboutlibrary.view.AboutView
import java.io.Serializable

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/05/18
 */
class AboutViewBuilder : Serializable {

    // -------------------- Info -------------------------
    var infoUrl: String? = null
        private set
    var infoJsonSring: String? = null
        private set
    var info: Info? = null
        private set

    // -------------------- Main Params ------------------
    var lang: String = "default"
        private set
    var idApp: String = ""
        private set

    // -------------------- About this app section -------
    var appBox = true
        private set
    var additionalAppButtons = listOf<AboutButtonBuilder>()
        private set

    // -------------------- Author section ---------------
    var authorBox = true
        private set
    var additionalAuthorButtons = listOf<AboutFabBuilder>()
        private set

    // -------------------- Other projects section -------
    var otherProjectsBox = true
        private set
    var additionalProjectButtons = mutableMapOf<String, List<AboutButtonBuilder>>()
        private set
    var excludeThisAppFromProjects = false
        private set

    fun withAdditionalAuthorButtons(buttons: List<AboutFabBuilder>): AboutViewBuilder {
        this.additionalAuthorButtons = buttons
        return this
    }

    fun withExcludeThisAppFromProjects(exclude: Boolean = false): AboutViewBuilder {
        this.excludeThisAppFromProjects = exclude
        return this
    }

    fun withAdditionalAppButtons(buttons: List<AboutButtonBuilder>): AboutViewBuilder {
        this.additionalAppButtons = buttons
        return this
    }

    fun withAdditionalProjectButtons(appId: String, buttons: List<AboutButtonBuilder>): AboutViewBuilder {
        this.additionalProjectButtons[appId] = buttons
        return this
    }

    fun withInfoUrl(infoUrl: String): AboutViewBuilder {
        this.infoUrl = infoUrl
        return this
    }

    fun withInfo(info: Info?): AboutViewBuilder {
        this.info = info
        return this
    }

    fun withInfoJsonSring(infoJsonSring: String): AboutViewBuilder {
        this.infoJsonSring = infoJsonSring
        return this
    }

    fun withAppId(idApp: String): AboutViewBuilder {
        this.idApp = idApp
        return this
    }

    fun withLang(lang: String = "default"): AboutViewBuilder {
        this.lang = lang
        return this
    }

    fun withAppBox(appBox: Boolean): AboutViewBuilder {
        this.appBox = appBox
        return this
    }

    fun withAuthorBox(authorBox: Boolean): AboutViewBuilder {
        this.authorBox = authorBox
        return this
    }

    fun withOtherProjectsBox(otherProjectsBox: Boolean): AboutViewBuilder {
        this.otherProjectsBox = otherProjectsBox
        return this
    }

    fun build(context: Context): View {
        return AboutView(context, this).create()
    }
}