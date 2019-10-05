package com.farmanlab.init_app

import android.app.Application
import com.farmanlab.init_app.di.applicationModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.instance
import timber.log.Timber

// open for debug application class
open class MyApplication : Application(), KodeinAware {

    override val kodein: Kodein by Kodein.lazy {
        import(androidXModule(this@MyApplication))
        import(applicationModule)
    }

    private val appInitializer: AppInitializer by instance()

    override fun onCreate() {
        super.onCreate()
        instance = this
        GlobalScope.launch {
            runCatching {
                appInitializer.init()
            }.fold(
                onSuccess = {},
                onFailure = { Timber.e(it) }
            )
        }
    }

    companion object {
        lateinit var instance: MyApplication
    }
}
