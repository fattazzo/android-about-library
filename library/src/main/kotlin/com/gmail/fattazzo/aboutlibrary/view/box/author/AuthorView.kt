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

package com.gmail.fattazzo.aboutlibrary.view.box.author

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.fattazzo.aboutlibrary.R
import com.gmail.fattazzo.aboutlibrary.builder.AboutFabBuilder
import com.gmail.fattazzo.aboutlibrary.builder.Action
import com.gmail.fattazzo.aboutlibrary.domain.Author
import com.gmail.fattazzo.aboutlibrary.utils.GravatarRetriver
import com.squareup.picasso.Picasso


/**
 * @author fattazzo
 *         <p/>
 *         date: 10/05/18
 */
class AuthorView(private val mContext: Context, private val author: Author?, private val additionalAuthorButtons: List<AboutFabBuilder>) {

    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mRootView: View = mInflater.inflate(R.layout.aboutlibrary_view_author, null)

    private var mCurrentAnimator: Animator? = null
    private val mShortAnimationDuration: Long = 500

    fun create(): View {
        val authorNameTV = mRootView.findViewById<TextView>(R.id.aboutlibrary_authorNameTV)
        authorNameTV.text = author?.name.orEmpty()

        val authorImageView = mRootView.findViewById<ImageView>(R.id.aboutlibrary_authorImageView)
        Picasso.get().load(GravatarRetriver.gravatarUrl(author?.email.orEmpty())).into(authorImageView)
        authorImageView.setOnClickListener { zoomImageFromThumb(authorImageView) }

        val fabsLayout = mRootView.findViewById<LinearLayout>(R.id.aboutlibrary_fabsLayout)

        author?.email?.let {
            val fab = AboutFabBuilder().withDrawable(R.drawable.aboutlibrary_email).withColor(R.color.aboutlibrary_email)
                    .withAction(object : Action {
                        override fun run(context: Context) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(author.email))
                            intent.type = "message/rfc822"
                            ContextCompat.startActivity(context, Intent.createChooser(intent, "Choose Mail Project"), null)
                        }
                    })
                    .build(mContext)
            fabsLayout.addView(fab)
        }

        author?.website?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_website, R.color.aboutlibrary_website, it)
            fabsLayout.addView(fab)
        }
        author?.github?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_github, R.color.aboutlibrary_github, it)
            fabsLayout.addView(fab)
        }
        author?.facebook?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_facebook, R.color.aboutlibrary_facebook, it)
            fabsLayout.addView(fab)
        }
        author?.twitter?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_twitter, R.color.aboutlibrary_twitter, it)
            fabsLayout.addView(fab)
        }
        author?.googleplus?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_gplus, R.color.aboutlibrary_googleplus, it)
            fabsLayout.addView(fab)
        }
        author?.linkedin?.let {
            val fab = createOpenLinkFab(R.drawable.aboutlibrary_linkedin, R.color.aboutlibrary_linkedin, it)
            fabsLayout.addView(fab)
        }

        additionalAuthorButtons.forEach { fabsLayout.addView(it.build(mContext)) }

        return mRootView
    }

    private fun createOpenLinkFab(drawableResId: Int, colorResId: Int, url: String): View {
        return AboutFabBuilder()
                .withDrawable(drawableResId)
                .withColor(colorResId)
                .withUrl(url)
                .build(mContext)
    }

    private fun zoomImageFromThumb(thumbView: ImageView) {
        mCurrentAnimator?.cancel()

        val expandedImageView = mRootView.findViewById(R.id.aboutlibrary_expanded_image) as ImageView
        expandedImageView.setImageDrawable(thumbView.drawable)

        val startBounds = Rect()
        val finalBounds = Rect()
        val globalOffset = Point()

        thumbView.getGlobalVisibleRect(startBounds)
        mRootView.findViewById<FrameLayout>(R.id.aboutlibrary_container).getGlobalVisibleRect(finalBounds, globalOffset)
        startBounds.offset(-globalOffset.x, -globalOffset.y)
        finalBounds.offset(-globalOffset.x, -globalOffset.y)

        val startScale: Float
        if ((finalBounds.width() / finalBounds.height()) > (startBounds.width() / startBounds.height())) {
            startScale = startBounds.height().toFloat() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth = (startWidth.minus(startBounds.width().toFloat())) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            startScale = startBounds.width().toFloat() / finalBounds.width()
            val startHeight = startScale * finalBounds.height()
            val deltaHeight = (startHeight - startBounds.height()) / 2
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        val set = AnimatorSet()
        set
                .play(ObjectAnimator.ofFloat<View>(expandedImageView, View.X,
                        startBounds.left.toFloat(), finalBounds.left.toFloat()))
                .with(ObjectAnimator.ofFloat<View>(expandedImageView, View.Y,
                        startBounds.top.toFloat(), finalBounds.top.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f))
        set.duration = mShortAnimationDuration
        set.interpolator = DecelerateInterpolator()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mCurrentAnimator = null
            }

            override fun onAnimationCancel(animation: Animator) {
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set

        expandedImageView.setOnClickListener {
            mCurrentAnimator?.cancel()

            val animatorSet = AnimatorSet()
            animatorSet.play(ObjectAnimator
                    .ofFloat(expandedImageView, View.X, startBounds.left.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.Y, startBounds.top.toFloat()))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_X, startScale))
                    .with(ObjectAnimator
                            .ofFloat(expandedImageView,
                                    View.SCALE_Y, startScale))
            animatorSet.duration = mShortAnimationDuration
            animatorSet.interpolator = DecelerateInterpolator()
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    thumbView.alpha = 1f
                    expandedImageView.visibility = View.GONE
                    mCurrentAnimator = null
                }
            })
            animatorSet.start()
            mCurrentAnimator = animatorSet
        }
    }
}