/*
 * Project: android-about-library
 * File: Project.kt
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

package com.gmail.fattazzo.aboutlibrary.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 09/05/18
 */
class Project : Serializable {

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("icon")
    @Expose
    lateinit var icon: String

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("playStoreUrl")
    @Expose
    var playStoreUrl: String? = null

    @SerializedName("githubUrl")
    @Expose
    var githubUrl: String? = null

    @SerializedName("wikiUrl")
    @Expose
    var wikiUrl: String? = null

    @SerializedName("appleStoreUrl")
    @Expose
    var appleStoreUrl: String? = null

    @SerializedName("websiteUrl")
    @Expose
    var websiteUrl: String? = null

    @SerializedName("i18n")
    @Expose
    var i18n: List<I18n> = listOf()

    fun getI18n(lang: String = I18n.DEFAULT): I18n? {
        i18n.forEach {
            if (it.lang == lang) {
                return it
            }
        }
        return null
    }

}