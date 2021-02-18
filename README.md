# JokeAPI-Android
This is an app using Android Architecture Components with Koin as a replacement for Dagger Hilt dependency injection.

## Introduction

### Functionality
The app is composed of 2 main screens

#### JokeListFragment
Displays 10 jokes from the [JokeAPI](https://github.com/Sv443/JokeAPI) in a scrollable list.
Each joke in the list displays a joke setup and is clickable, which navigates to the joke detail view.

#### JokeDetailFragment
Displays the selected joke's category, setup, and delivery.
A back arrow will be displayed for navigating back to the joke list

### Building
You can open the project in Android Studio and press run

### Testing
This project uses both instrumentation tests that run on the device and local unit tests that can be run on the computer

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  * [Data Binding][11] - Declaratively bind observable data to UI elements.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [Navigation][14] - Handle everything needed for in-app navigation.
  * [Room][16] - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [UI][30] - Details on why and how to use UI Components in your apps - together or separate
  * [Fragment][34] - A basic unit of composable UI.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party and miscellaneous libraries
  * [Koin][90] for dependency injection
  * [Retrofit][91] for networking
  * [Timber][92] for additional logging capabilities
  * [Mockito Kotlin][93] for improved Mockito testing in Kotlin
  * [Mockito][94] for general Mockito testing capabilities
  * [Gson][95] for parsing JSON response from the JokeAPI

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[30]: https://developer.android.com/guide/topics/ui
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[90]: https://github.com/InsertKoinIO/koin
[91]: https://github.com/square/retrofit
[92]: https://github.com/JakeWharton/timber
[93]: https://github.com/mockito/mockito-kotlin
[94]: https://github.com/mockito/mockito
[95]: https://github.com/google/gson
 
# Notes
* Most commits are missing due to switching over to a different repository. Normally, I commit early and commit often.
* I began working on this project with the intention of familiarizing myself with the new Dagger Hilt framework for dependency injection and Kotlin's newer experimental coroutines.
  After working on learning the new frameworks and beginning the process of building out the app, I was beginning to feel rushed for time a bit given the scope of the app with testing.
  I eventually decided to pivot into something I was more familiar with, which is Koin. The Dagger Hilt framework seems pretty awesome and I look forward to having more time to acquaint myself with it.
* Hypothetical next steps given more time:
  * Continue building out the test framework to allow for even greater coverage
  * Include Test Coverage reports from Jacoco to ensure coverage
  * Implement refresh and scroll-down-to-refresh for a better user experience
  * Continue tweaking the UI to more of a polished look
  * Introduce some animations around the delivery of the joke (it's supposed to be funny, why not enhance it with some cool graphics and animations?)
  * Add the ability to favorite and save jokes to the local database (or Firebase potentially)
