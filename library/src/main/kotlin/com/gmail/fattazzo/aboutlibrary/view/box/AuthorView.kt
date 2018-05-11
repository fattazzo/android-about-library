/*
 * Project: android-about-library
 * File: AuthorView.kt
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
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.domain.Author
import com.gmail.fattazzo.aboutlibrary.utils.GravatarRetriver
import com.gmail.fattazzo.aboutlibrary.utils.Utils
import com.squareup.picasso.Picasso

/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class AuthorView(private val mContext: Context, private val author: Author?) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_author, null)

    fun create(): View {
        val authorNameTV = mRootView.findViewById<TextView>(R.id.authorNameTV)
        authorNameTV.text = author?.name.orEmpty()

        val authorImageView = mRootView.findViewById<ImageView>(R.id.authorImageView)
        Picasso.get().load(GravatarRetriver.gravatarUrl(author?.email.orEmpty())).into(authorImageView)

        bindEmailButton()
        bindOpenLinkButton(R.id.websiteFAB, author?.website.isNullOrBlank(), author?.website)
        bindOpenLinkButton(R.id.githubFAB, author?.github.isNullOrBlank(), author?.github)
        bindOpenLinkButton(R.id.facebookFAB, author?.facebook.isNullOrBlank(), author?.facebook)
        bindOpenLinkButton(R.id.twitterFAB, author?.twitter.isNullOrBlank(), author?.twitter)
        bindOpenLinkButton(R.id.googleplusFAB, author?.googleplus.isNullOrBlank(), author?.googleplus)
        bindOpenLinkButton(R.id.linkedinFAB, author?.linkedin.isNullOrBlank(), author?.linkedin)

        return mRootView
    }

    private fun bindEmailButton() {

        val emailFAB = mRootView.findViewById<FloatingActionButton>(R.id.emailFAB)
        if (author?.email.isNullOrBlank()) {
            emailFAB.visibility = View.GONE
        } else {
            emailFAB.visibility = View.VISIBLE
            emailFAB.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(this.author?.email))
                intent.type = "message/rfc822"
                ContextCompat.startActivity(mContext, Intent.createChooser(intent, "Choose Mail Project"), null)
            }
        }
    }

    private fun bindOpenLinkButton(fabResId: Int, hide: Boolean, link: String?) {
        val fab = mRootView.findViewById<FloatingActionButton>(fabResId)
        if (hide) {
            fab.visibility = View.GONE
        } else {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener {
                Utils.openLink(mContext, link)
            }
        }
    }
}