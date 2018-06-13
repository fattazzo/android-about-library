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

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import com.gmail.fattazzo.aboutlibrary.activity.AboutActivity
import com.gmail.fattazzo.aboutlibrary.builder.AboutViewBuilder
import com.gmail.fattazzo.aboutlibrary.domain.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.ViewById
import org.androidannotations.annotations.res.StringArrayRes


@EActivity(R.layout.activity_main)
open class MainActivity : AppCompatActivity() {

    // Main params
    @ViewById
    internal lateinit var langSpinner: Spinner
    @ViewById
    internal lateinit var appSpinner: Spinner
    @ViewById
    internal lateinit var flatButtonsCheckBox: CheckBox

    // Section
    @ViewById
    internal lateinit var appCheckBox: CheckBox
    @ViewById
    internal lateinit var authorCheckBox: CheckBox
    @ViewById
    internal lateinit var otherProjectsCheckBox: CheckBox

    // Others
    @ViewById
    internal lateinit var errorViewCheckBox: CheckBox

    @StringArrayRes(R.array.lang_description)
    internal lateinit var langDesArrayRes: Array<String>
    @StringArrayRes(R.array.lang_code)
    internal lateinit var langCodeArrayRes: Array<String>

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
        var aboutViewBuilder = if (errorViewCheckBox.isChecked) {
            AboutViewBuilder().withInfoUrl("wrong url")
        } else {
            AboutViewBuilder().withInfo(createInfoSample())
        }

        aboutViewBuilder = aboutViewBuilder
                // ------------- Main params -------------------
                .withAppId(projectsIdArrayRes[appSpinner.selectedItemPosition])
                .withLang(langCodeArrayRes[langSpinner.selectedItemPosition])
                .withFlatStyleButtons(flatButtonsCheckBox.isChecked)
                // ------------- About this app section --------
                .withAppBox(appCheckBox.isChecked)
                // ------------- Author section ----------------
                .withAuthorBox(authorCheckBox.isChecked)
                // ------------- Other projects section --------
                .withOtherProjectsBox(otherProjectsCheckBox.isChecked)
                .withExcludeThisAppFromProjects(true)

        val intent = Intent(this, AboutActivity::class.java)
                .apply { putExtra(AboutActivity.EXTRA_BUILDER, aboutViewBuilder) }
        startActivity(intent)
    }

    @Click
    fun resetButtonClicked() {

        langSpinner.setSelection(0)
        appSpinner.setSelection(0)
        flatButtonsCheckBox.isChecked = false

        appCheckBox.isChecked = true
        authorCheckBox.isChecked = true
        otherProjectsCheckBox.isChecked = true

        errorViewCheckBox.isChecked = false
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
            group = Group.APP
            icon = "https://lh5.ggpht.com/a9f5u65dd-GgFGbLaDO5XauCNcjtJqOkSDqR_xDJ9vbT4MqutzLz0dfsWBtGBH2Ij6sq=h150"
            playStoreUrl = "https://play.google.com/store/apps/details?id=com.gmail.fattazzo.meteo"
            githubUrl = "https://github.com/fattazzo/meteo"
            image = "https://lh5.ggpht.com/xpKYeB9Nmbv2ZLDLPJT52hIpVoAAbPLAm-_jCPSTuFutNynZ6WYujmkWXcFiQjcQTRk=h250"
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
            group = Group.APP
            icon = "https://raw.githubusercontent.com/wiki/fattazzo/total-gp-world/images/logo.png"
            playStoreUrl = "https://play.google.com/store/apps/details?id=com.gmail.fattazzo.formula1world"
            githubUrl = "https://github.com/fattazzo/total-gp-world"
            wikiUrl = "https://github.com/fattazzo/total-gp-world/wiki"
            image = "https://lh3.googleusercontent.com/-x9tE1XBxqvwgKc6G6Syq1jU_gzkdMYaFWFyyuW4xT-lhOJdoaBBaZyOzeshn-WD9mQ=h250"
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
            group = Group.LIBRARY
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
