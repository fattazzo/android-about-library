/*
 * Project: android-about-library
 * File: MainActivity.kt
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

package com.gmail.fattazzo.aboutlibrary.sample

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.gmail.fattazzo.aboutlibrary.view.AboutView
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.ViewById
import org.androidannotations.annotations.res.StringArrayRes


@EActivity(R.layout.activity_main)
open class MainActivity : AppCompatActivity() {

    @ViewById
    internal lateinit var aboutLayout: LinearLayout

    @ViewById
    internal lateinit var paramsLayout: ScrollView

    @ViewById
    internal lateinit var appCheckBox: CheckBox
    @ViewById
    internal lateinit var authorCheckBox: CheckBox
    @ViewById
    internal lateinit var otherProjectsCheckBox: CheckBox

    @ViewById
    internal lateinit var langSpinner: Spinner

    @StringArrayRes(R.array.lang_description)
    internal lateinit var langDesArrayRes: Array<String>
    @StringArrayRes(R.array.lang_code)
    internal lateinit var langCodeArrayRes: Array<String>

    @ViewById
    internal lateinit var appSpinner: Spinner

    @StringArrayRes(R.array.projects_list_description)
    internal lateinit var projectsDescArrayRes: Array<String>
    @StringArrayRes(R.array.projects_list_id)
    internal lateinit var projectsIdArrayRes: Array<String>

    @AfterViews
    fun initViews() {
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, langDesArrayRes)
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = langAdapter

        val appAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projectsDescArrayRes)
        appAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        appSpinner.adapter = appAdapter
    }

    @Click
    fun loadButtonClicked() {
        paramsLayout.visibility = View.GONE

        aboutLayout.visibility = View.VISIBLE
        aboutLayout.removeAllViews()

        val aboutView = AboutView(this)
                .withInfoUrl("https://gist.githubusercontent.com/fattazzo/d6aa41128c39b4882c0b6bd232984cfb/raw")
                .withAppId(projectsIdArrayRes[appSpinner.selectedItemPosition])
                .withLang(langCodeArrayRes[langSpinner.selectedItemPosition])
                .withAppBox(appCheckBox.isChecked)
                .withAuthorBox(authorCheckBox.isChecked)
                .withOtherProjectsBox(otherProjectsCheckBox.isChecked)

        aboutLayout.addView(aboutView.create())
    }

    @Click
    fun resetButtonClicked() {
        aboutLayout.visibility = View.GONE
        aboutLayout.removeAllViews()

        paramsLayout.visibility = View.VISIBLE

        appCheckBox.isChecked = true
        authorCheckBox.isChecked = true
        otherProjectsCheckBox.isChecked = true
        langSpinner.setSelection(0)
    }
}
