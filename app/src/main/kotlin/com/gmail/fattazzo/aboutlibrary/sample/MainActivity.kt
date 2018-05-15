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
import com.gmail.fattazzo.aboutlibrary.domain.Author
import com.gmail.fattazzo.aboutlibrary.domain.I18n
import com.gmail.fattazzo.aboutlibrary.domain.Info
import com.gmail.fattazzo.aboutlibrary.domain.Project
import com.gmail.fattazzo.aboutlibrary.view.AboutView
import org.androidannotations.annotations.*
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

    @JvmField
    @InstanceState
    var aboutView: AboutView? = null


    @AfterViews
    fun initViews() {
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, langDesArrayRes)
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = langAdapter

        val appAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projectsDescArrayRes)
        appAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        appSpinner.adapter = appAdapter

        if (aboutView != null) {
            loadButtonClicked()
        } else {
            aboutLayout.visibility = View.GONE
            paramsLayout.visibility = View.VISIBLE
        }
    }

    @Click
    fun loadButtonClicked() {
        paramsLayout.visibility = View.GONE

        aboutLayout.visibility = View.VISIBLE
        aboutLayout.removeAllViews()

        if (aboutView == null) {
            aboutView = AboutView(this)
                    // ------------- Info --------------------------
                    //.withInfoUrl("https://gist.githubusercontent.com/fattazzo/d6aa41128c39b4882c0b6bd232984cfb/raw")
                    .withInfo(createInfoSample())
                    // ------------- Main params -------------------
                    .withAppId(projectsIdArrayRes[appSpinner.selectedItemPosition])
                    .withLang(langCodeArrayRes[langSpinner.selectedItemPosition])
                    // ------------- About this app section --------
                    .withAppBox(appCheckBox.isChecked)
                    // ------------- Author section ----------------
                    .withAuthorBox(authorCheckBox.isChecked)
                    // ------------- Other projects section --------
                    .withOtherProjectsBox(otherProjectsCheckBox.isChecked)
                    .withExcludeThisAppFromProjects(true)
        }

        aboutLayout.addView(aboutView!!.create())
    }

    @Click
    fun resetButtonClicked() {
        aboutView = null

        aboutLayout.visibility = View.GONE
        aboutLayout.removeAllViews()

        paramsLayout.visibility = View.VISIBLE

        appCheckBox.isChecked = true
        authorCheckBox.isChecked = true
        otherProjectsCheckBox.isChecked = true
        langSpinner.setSelection(0)
    }

    private fun createInfoSample(): Info {
        val author = Author().apply {
            name = "Gianluca Fattarsi"
            email = "fattazzo82@gmail.com"
            github = "https://github.com/fattazzo"
            facebook = "https://www.facebook.com/Fattazzo"
            googleplus = "https://plus.google.com/+GianlucaFattarsi"
            linkedin = "https://www.linkedin.com/in/gianluca-fattarsi"
        }

        val meteoProject = Project().apply {
            id = "com.gmail.fattazzo.meteo"
            icon = "https://lh5.ggpht.com/a9f5u65dd-GgFGbLaDO5XauCNcjtJqOkSDqR_xDJ9vbT4MqutzLz0dfsWBtGBH2Ij6sq=h150"
            playStoreUrl = "https://play.google.com/store/apps/details?id=com.gmail.fattazzo.meteo"
            githubUrl = "https://github.com/fattazzo/meteo"
            i18n = listOf(I18n().apply {
                lang = "default"
                title = "Weather of Trentino"
                description = "Weather information obtained through Forecasts and Planning Office of the Autonomous Province of Trento from site: http://dati.trentino.it provided by www.meteotrentino.it"
            }, I18n().apply {
                lang = "it"
                title = "Meteo del Trentino"
                description = "Informazioni meteo ottenute tramite l'Ufficio Previsioni e Pianificazione della Provincia Autonoma di Trento dal sito: http://dati.trentino.it forniti da www.meteotrentino.it"
            })
        }

        val totalGPProject = Project().apply {
            id = "com.gmail.fattazzo.formula1world"
            icon = "https://raw.githubusercontent.com/wiki/fattazzo/total-gp-world/images/logo.png"
            playStoreUrl = "https://play.google.com/store/apps/details?id=com.gmail.fattazzo.formula1world"
            githubUrl = "https://github.com/fattazzo/total-gp-world"
            wikiUrl = "https://github.com/fattazzo/total-gp-world/wiki"
            i18n = listOf(I18n().apply {
                lang = "default"
                title = "Total GP world"
                description = "With Total GP world you will have all the informations about formula 1 you need: results, rankings, updated timetables and comparisons from the season 1950 to the present.\\nThe app is still in the beginning, so updates will follow, and will add more and more content from time to time."
            }, I18n().apply {
                lang = "it"
                title = "Total GP world"
                description = "Con Total GP world potrai avere tutte le informazioni sul mondo della formula 1 che ti servono: risultati, classifiche, calendario aggiornato e comparazioni dalla stagione 1950 fino ad oggi."
            })
        }

        val aboutLibraryProject = Project().apply {
            id = "com.gmail.fattazzo.aboutlibrary"
            icon = "https://raw.githubusercontent.com/wiki/fattazzo/android-about-library/images/info.png"
            githubUrl = "https://github.com/fattazzo/android-about-library"
            wikiUrl = "https://github.com/fattazzo/android-about-library/wiki"
            websiteUrl = "https://fattazzo.github.io/android-about-library/"
            i18n = listOf(I18n().apply {
                lang = "default"
                title = "Android About Library"
                description = "The AboutLibraries library allows you to easily generate beautiful about view fully customizable with less effort and code."
            }, I18n().apply {
                lang = "it"
                title = "Android About Library"
                description = "La libreria AboutLibraries permette di generare in modo semplice un pagina di about interamente customizzabile con poco sforzo e codice."
            })
        }

        return Info().apply {
            this.author = author
            this.projects = listOf(totalGPProject, meteoProject, aboutLibraryProject)
        }
    }
}
