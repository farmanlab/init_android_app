package com.farmanlab.init_app

import android.content.Context
import com.farmanlab.init_app.data.gamedatastatus.GameDataStatusDataSource
import com.farmanlab.init_app.data.user.UserDataSource
import com.farmanlab.init_app.remotedata.RemoteGameDataUpdateWorker
import com.farmanlab.init_app.util.ad.AppAdManager
import com.google.ads.consent.ConsentStatus
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDateTime
import timber.log.Timber

interface AppInitializer {
    suspend fun init()
}

class AppInitializerImpl(
    private val context: Context,
    private val gameDataStatusDataSource: GameDataStatusDataSource,
    private val userDataSource: UserDataSource
) : AppInitializer, KodeinAware {
    override val kodein: Kodein by kodein(context)
    private val adManager: AppAdManager by instance(arg = context)

    override suspend fun init() {
        AndroidThreeTen.init(context)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val status = gameDataStatusDataSource.fetchStatus()
        val user = userDataSource.fetchUserData()
        if (!status.dataInitialized) {
            RemoteGameDataUpdateWorker.launchWorkerJustNow(context)
        } else {
            RemoteGameDataUpdateWorker.launchWorker(context, LocalDateTime.now())
        }

        if (user.firstLaunchDateTime == null) {
            userDataSource.setFirstLaunchDateTime(LocalDateTime.now())
        }
        initAd()
    }

    private suspend fun initAd() {
        when (adManager.requestConsentInfo()) {
            ConsentStatus.PERSONALIZED, ConsentStatus.NON_PERSONALIZED -> {
                adManager.initMobileAd()
            }
            else -> {
            }
        }
    }
}
