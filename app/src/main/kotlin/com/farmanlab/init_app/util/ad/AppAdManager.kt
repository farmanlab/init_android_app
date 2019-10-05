package com.farmanlab.init_app.util.ad

import android.content.Context
import androidx.core.os.bundleOf
import com.farmanlab.init_app.R
import com.google.ads.consent.*
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AppAdManager {
    suspend fun requestConsentInfo(): ConsentStatus?
    suspend fun showFormAndResult(): ConsentStatus?
    fun initMobileAd()
    fun getAdRequestBuilder(consentStatus: ConsentStatus?): AdRequest.Builder
}

class AppAdManagerImpl(
    private val context: Context,
    private val consentInformation: ConsentInformation
) : AppAdManager {
    override suspend fun requestConsentInfo(): ConsentStatus? {
        val consentStatus =
            consentInformation.requestConsentInfoUpdateAsync(arrayOf("pub-9556084848462636"))
        consentInformation.consentStatus = consentStatus
        return if (consentInformation.isRequestLocationInEeaOrUnknown) {
            consentStatus
        } else {
            null
        }
    }

    override suspend fun showFormAndResult(): ConsentStatus? =
        ConsentForm.Builder(context, URL(context.getString(R.string.uri_privacy_policy))).loadAsync()

    override fun initMobileAd() {
        MobileAds.initialize(context, context.getString(R.string.ad_mob_app_key))
    }

    override fun getAdRequestBuilder(consentStatus: ConsentStatus?): AdRequest.Builder {
        val builder = AdRequest.Builder()
        if (consentStatus == ConsentStatus.NON_PERSONALIZED) {
            builder.addNetworkExtrasBundle(AdMobAdapter::class.java, bundleOf(Pair("npa", "1")))
        }
        return builder
    }
}

private suspend fun ConsentInformation.requestConsentInfoUpdateAsync(publisherIds: Array<String>): ConsentStatus? =
    suspendCoroutine {
        requestConsentInfoUpdate(publisherIds, object : ConsentInfoUpdateListener {
            override fun onConsentInfoUpdated(consentStatus: ConsentStatus?) {
                this@requestConsentInfoUpdateAsync.consentStatus = consentStatus
                it.resume(consentStatus)
            }

            override fun onFailedToUpdateConsentInfo(reason: String?) {
                it.resumeWithException(ConsentInformationException(reason))
            }
        })
    }

private suspend fun ConsentForm.Builder.loadAsync(): ConsentStatus? =
    suspendCoroutine {
        var form: ConsentForm? = null
        form = withListener(object : ConsentFormListener() {
            override fun onConsentFormLoaded() {
                form?.show()
            }

            override fun onConsentFormError(reason: String?) {
                it.resumeWithException(ConsentFormException(reason))
            }

            override fun onConsentFormClosed(
                consentStatus: ConsentStatus?,
                userPrefersAdFree: Boolean?
            ) {
                it.resume(consentStatus)
            }
        })
            .withNonPersonalizedAdsOption()
            .withPersonalizedAdsOption()
            .build()
        form.load()
    }

class ConsentInformationException(msg: String?) : Exception(msg)
class ConsentFormException(msg: String?) : Exception(msg)
