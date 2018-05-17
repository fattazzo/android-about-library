/*
 * Project: android-about-library
 * File: InfoDownloaderTask.kt
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

package com.gmail.fattazzo.aboutlibrary.loader

import android.os.AsyncTask
import com.gmail.fattazzo.aboutlibrary.domain.Info
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class InfoDownloaderTask(private var closure: Closure<Info?>) : AsyncTask<URL?, Void, Info?>() {

    override fun doInBackground(vararg params: URL?): Info? {

        return try {
            val stream = getInputStreamFromURL(params.firstOrNull())

            InfoBuilder.build(stream!!)
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: Info?) {
        closure.onPostExecute(result)
    }

    override fun onCancelled(result: Info?) {
        closure.onCancel()
    }

    override fun onCancelled() {
        closure.onCancel()
    }

    /**
     * Get stream from input url
     *
     * @param url url
     * @return stream loaded
     */
    private fun getInputStreamFromURL(url: URL?): InputStream? {
        return if (url != null) {
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 15000
            conn.readTimeout = 15000

            conn.inputStream
        } else {
            null
        }
    }
}