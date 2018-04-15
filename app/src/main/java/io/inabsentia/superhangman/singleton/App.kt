package io.inabsentia.superhangman.singleton

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences

@SuppressLint("Registered")
class App private constructor() : Application() {

    var username: String? = null
    var password: String? = null

    private var prefs: SharedPreferences? = null

    companion object {
        @get:Synchronized
        var instance: App? = null
            private set

        init {
            instance = App()
        }
    }

}