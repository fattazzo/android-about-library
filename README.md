# android-about-library

The AboutLibraries library allows you to easily generate beautiful about view fully customizable with less effort and code.

* [Overview](#overview)
* [Include in your project](#include-in-your-project)
* [Simple usage](#simple-usage)
* [Screenshots from sample app](#screenshots-from-sample-app)

## Overview

Here's a quick overview of functions it include:

* About this app section
  - Icon
  - Title
  - Default buttons ( like Google Play, GitHub, Apple Store, Website, etc... )
  - Custom buttons ( user difined buttons like Error Reporter, Licence, etc... )
* Author section
  - Profile image using Gravatar
  - Name
  - FloatingActionButton for social accounts and contacts ( email, GitHub, Facebook, Twitter, Google+, LinkedIn and Website)
* Other projects section
  - Icon
  - Name
  - Description
  - Store and project source button
* Multiple screen support

All the information can be displayed with multilanguage support!


## Include in your project

Available on JitPack

```javascript
repositories {
        maven { url "https://jitpack.io" }
    }
```

add the following dependency to your `build.gradle`

```javascript
implementation "com.github.fattazzoandroid-about-library:0.3"

//required support lib modules
implementation "com.android.support:appcompat-v7:${versions.supportLib}"
implementation "com.android.support:gridlayout-v7:${versions.supportLib}"
implementation "com.android.support:design:${versions.supportLib}"
implementation "com.android.support:cardview-v7:${versions.supportLib}"
```
Verify the latest release at https://github.com/fattazzo/android-about-library/releases/latest

## Simple usage

```java
class AboutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val aboutViewBuilder = AboutViewBuilder()
                        .withInfoUrl(INFO_URL)
                        .withAppId(this@AboutActivity.applicationContext.packageName)
                        .withLang("it")
                        .withAppBox(true) // default true
                        .withAuthorBox(true) // default true
                        .withOtherProjectsBox(true) // default true

        setContentView(aboutViewBuilder.build(this))
    }
}
```

**Explanation**
* **withInfoUrl**: Url of info configuration. The configuration can be provided as url, jsonString or as a simple object in the app. [See the wiki](https://github.com/fattazzo/android-about-library/wiki/Data-source-configuration) to learn more.
* **withAppId**: App id used for display informations in the "About this app section"
* **withLang**: Language of about view
* **withAppBox**: Toggle "About this app section"
* **withAuthorBox**: Toggle "Author section"
* **withOtherProjectsBox**: Toggle "Other projects section"

For advanced configuration please refer to the wiki.

## Screenshots from sample app

Standard screen

![](https://github.com/fattazzo/android-about-library/wiki/images/screenshot01.png)

![](https://github.com/fattazzo/android-about-library/wiki/images/screenshot02.png)

![](https://github.com/fattazzo/android-about-library/wiki/images/screenshot03.png)

Landscape standard screen

![](https://github.com/fattazzo/android-about-library/wiki/images/screenshot05.png)

Landscape tablet screen

![](https://github.com/fattazzo/android-about-library/wiki/images/screenshot04.png)

## License

```
MIT License

Copyright (c) 2018 Gianluca Fattarsi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
