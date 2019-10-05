package com.farmanlab.init_app.di

import android.content.Context
import com.farmanlab.init_app.util.ad.AppAdManager
import com.farmanlab.init_app.util.ad.AppAdManagerImpl
import com.google.ads.consent.ConsentInformation
import com.google.ads.consent.DebugGeography
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.multiton

private const val MODULE_NAME = "ad_manager"

val adManagerModule = Kodein.Module("ad_manager") {
    bind<AppAdManager>() with multiton { context: Context ->
        val appAdManagerImpl = AppAdManagerImpl(
            context = context,
            consentInformation = ConsentInformation.getInstance(context).apply {
                addTestDevice("865C5068284B6B4F153F987FB690722F")
                debugGeography = DebugGeography.DEBUG_GEOGRAPHY_EEA
            }
        )
        appAdManagerImpl
    }
}
