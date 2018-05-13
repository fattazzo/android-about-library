# android-about-library

The AboutLibraries library allows you to easily generate beautiful about view fully customizable with less effort and code.

## Little overview

Here's a quick overview of functions it include:

* About this app section
  - Icon
  - Title
  - Legacy buttons ( like Google Play, GitHub, Apple Store, Website, etc... )
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

All the information can be displayed with multilanguage support!

## Screenshots from sample app

![image](https://github.com/fattazzo/android-about-library/wiki/images/screenshot01.png) <!-- .element height="50%" width="50%" -->


## Include in your project

Available on JitPack

```javascript
repositories {
        maven { url "https://jitpack.io" }
    }
```

add the following dependency to your `build.gradle`

```javascript
implementation "com.github.fattazzoandroid-about-library:7823306ae7"

//required support lib modules
implementation "com.android.support:appcompat-v7:${versions.supportLib}"
implementation "com.android.support:gridlayout-v7:${versions.supportLib}"
implementation "com.android.support:design:${versions.supportLib}"
implementation "com.android.support:cardview-v7:${versions.supportLib}"
```

## Simple usage

```java
class AboutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val aboutView = AboutView(this)
                .withInfoUrl(INFO_URL)
                .withAppId(this@AboutActivity.applicationContext.packageName)
                .withLang(Locale.getDefault().language)
                .withAppBox(appCheckBox.isChecked)
                .withAuthorBox(authorCheckBox.isChecked)
                .withOtherProjectsBox(otherProjectsCheckBox.isChecked)

        setContentView(aboutView.create())
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
