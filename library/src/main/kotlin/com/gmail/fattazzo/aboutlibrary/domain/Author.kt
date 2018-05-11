/*
 * Project: android-about-library
 * File: Author.kt
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
class Author : Serializable {

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("email")
    @Expose
    lateinit var email: String

    @SerializedName("github")
    @Expose
    var github: String? = null

    @SerializedName("facebook")
    @Expose
    var facebook: String? = null

    @SerializedName("twitter")
    @Expose
    var twitter: String? = null

    @SerializedName("googleplus")
    @Expose
    var googleplus: String? = null

    @SerializedName("linkedin")
    @Expose
    var linkedin: String? = null

    @SerializedName("website")
    @Expose
    var website: String? = null

}