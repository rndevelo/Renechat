package com.rndeveloper.renechat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReneChatApp : Application() {

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            plant(DebugTree())
//        }
    }
}