/*
 * Project: android-about-library
 * File: GravatarRetriver.kt
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

package com.gmail.fattazzo.aboutlibrary.utils

import java.security.MessageDigest

/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
object GravatarRetriver {

    enum class GravatarDefault(val str: String) {
        ERROR_404("404"),
        MYSTERY("mm"),
        IDENTICON("identicon"),
        MONSTER("monsterid"),
        WAVATAR("wavatar"),
        RETRO("retro"),
        BLANK("blank"),
    }

    fun gravatarUrl(email: String,
                    size: Int = 80,
                    default: GravatarDefault = GravatarDefault.WAVATAR,
                    secure: Boolean = false): String {

        val hash = email.trim().toLowerCase().md5()
        val protocol = if (secure) "https" else "http"
        return "$protocol://www.gravatar.com/avatar/$hash?s=$size&d=${default.str}"
    }

    @Throws(Exception::class)
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(this.toByteArray())

        val byteData = md.digest()
        val hexString = StringBuffer()
        for (i in byteData.indices) {
            val hex = Integer.toHexString(255 and byteData[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }

        return hexString.toString()
    }
}