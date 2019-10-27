@Suppress("unused")
object Versions {
    // region core
    const val Kotlin = "1.3.50"
    const val Lifecycle = "2.2.0-rc01"
    const val Room = "2.2.1"
    const val Navigation = "2.1.0"
    const val Coroutines = "1.3.2"
    const val WorkManager = "2.1.0"

    // Support Library
    const val KtxSupport = "1.2.0-beta01"
    const val RecyclerView = "1.1.0-rc01"
    const val JetpackActivity = "1.1.0-rc01"
    const val AppCompat = "1.1.0"
    const val ConstraintLayout = "2.0.0-beta3"
    const val Material = "1.2.0-alpha01"

    // logging
    const val Timber = "4.7.1"

    // util
    const val Threeten = "1.2.1"

    // test
    const val TestExt = "1.1.2-alpha02"
    const val SupportTest = "1.3.0-alpha02"
    const val Espresso = "3.3.0-alpha02"
    const val Mockito = "3.1.0"
    const val MockitoKotlin = "1.5.0"
    const val Robolectric = "4.3.1"
    const val Truth = "1.0"

    // ktlint
    const val KtLintGradle = "8.2.0"
    const val KtLint = "0.31.0"

    // OkHttp3
    const val OkHttp3 = "4.2.2"

    // Retrofit
    const val Retrofit = "2.6.2"
    const val RetrofitKotlinCoroutines = "0.9.2"

    // Moshi
    const val Moshi = "1.8.0"
    const val Kotshi = "2.0.1"

    // Firebase
    const val FirebaseRemoteConfig = "19.0.3"
    const val FirebaseAd = "18.2.0"
    const val FirebaseCore = "17.2.0"
    const val FirebaseKtx = "19.2.0"

    // DI
    const val Kodein = "6.4.1"

    // UI
    const val Groupie = "2.7.1"
    const val Glide = "4.10.0"
    const val flexBoxLayout = "1.1.1"

    // Ad
    const val AdConsent = "1.0.7"

    // GooglePlayServices
    const val GooglePlayServices = "17.0.0"

    // debug
    const val Hyperion = "0.9.27"

    // My...
    const val MyAACExtensions = "1.0.1"
    const val MyCommonExtensions = "1.0.2"
    const val MyAsyncPermissions = "1.0.1"
    const val MyCoroutineDialogFragment = "1.0.1"
    const val MyKodeinExtensions = "1.0.0"
}

object Dependencies {
    enum class Implementation(val value: String) {
        // kotlin
        KOTLIN_STD_LIB("org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin}"),
        KOTLIN_COROUTINES("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"),
        KOTLIN_ANDROID_COROUTINEE("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines}"),

        // utils
        THREE_TEN("com.jakewharton.threetenabp:threetenabp:${Versions.Threeten}"),
        LIVE_DATA_EXT("com.snakydesign.livedataextensions:lives:1.3.0"),
//        GUAVA("com.google.guava:guava:27.0.1-android"),

        // logging
        TIMBER("com.jakewharton.timber:timber:${Versions.Timber}"),

        // di
        KODEIN("org.kodein.di:kodein-di-generic-jvm:${Versions.Kodein}"),
        KODEIN_ANDROID("org.kodein.di:kodein-di-framework-android-x:${Versions.Kodein}"),

        // UI
        GROUPIE("com.xwray:groupie:${Versions.Groupie}"),
        GROUPIE_KTX("com.xwray:groupie-kotlin-android-extensions:${Versions.Groupie}"),
        GROUPIE_DATA_BINDING("com.xwray:groupie-databinding:${Versions.Groupie}"),
        FLEX_BOX("com.google.android:flexbox:${Versions.flexBoxLayout}"),
        GLIDE("com.github.bumptech.glide:glide:${Versions.Glide}"),
        GLIDE_OK_HTTP_INTEGRATION("com.github.bumptech.glide:okhttp3-integration:${Versions.Glide}"),

        // Retrofit
        RETROFIT("com.squareup.retrofit2:retrofit:${Versions.Retrofit}"),
        RETROFIT_MOSHI_ADAPTER("com.squareup.retrofit2:converter-moshi:${Versions.Retrofit}"),
        RETROFIT_KOTLIN_COROUTINES("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.RetrofitKotlinCoroutines}"),

        OK_HTTP("com.squareup.okhttp3:okhttp:${Versions.OkHttp3}"),
        OK_HTTP_LOGGING_INTERCEPTOR("com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp3}"),

        // parser
        MOSHI("com.squareup.moshi:moshi-kotlin:${Versions.Moshi}"),
        MOSHI_ADAPTERS("com.squareup.moshi:moshi-adapters:${Versions.Moshi}"),
        KOSHI("se.ansman.kotshi:api:${Versions.Kotshi}"),

        // JETPACK
        // Support Library
        JETPACK_CORE_KTX("androidx.core:core-ktx:${Versions.KtxSupport}"),
        JETPACK_ANNOTATION("androidx.annotation:annotation:1.0.1"),
        JETPACK_APPCOMPAT("androidx.appcompat:appcompat:${Versions.AppCompat}"),
        JETPACK_ACTIVITY("androidx.activity:activity:${Versions.JetpackActivity}"),
        JETPACK_FRAGMENT_KTX("androidx.fragment:fragment-ktx:${Versions.KtxSupport}"),
        JETPACK_RECYCLER_VIEW("androidx.recyclerview:recyclerview:${Versions.RecyclerView}"),
        JETPACK_CONSTATINT_LAYOUT("androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"),
        JETPACK_CARD_VIEW("androidx.cardview:cardview:1.0.0"),
        JETPACK_WORK_MANAGER("androidx.work:work-runtime-ktx:${Versions.WorkManager}"),

        // Material Design
        MATERIAL("com.google.android.material:material:${Versions.Material}"),

        // Arch
        JETPACK_LIFECYCLE_EXTENSIONS("androidx.lifecycle:lifecycle-extensions:${Versions.Lifecycle}"),
        JETPACK_LIFECYCLE_VIEWMODEL_KTX("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle}"),
        JETPACK_LIFECYCLE_LIVEDATA("androidx.lifecycle:lifecycle-livedata:${Versions.Lifecycle}"),
        JETPACK_ROOM_KTX("androidx.room:room-ktx:${Versions.Room}"),

        // OSS LICENSES
        OSS_LICENSES("com.google.android.gms:play-services-oss-licenses:${Versions.GooglePlayServices}"),

        // MY
        MY_AAC_EX("com.farmanlab:aac-extensions:${Versions.MyAACExtensions}"),
        MY_COMMON_EX("com.farmanlab:common-extensions:${Versions.MyCommonExtensions}"),
        MY_ASYNC_PERMISSIONS("com.farmanlab:async-permissions:${Versions.MyAsyncPermissions}"),
        MY_COROUTINE_DIALOG_FRAGMENT("com.farmanlab:coroutine-dialog-fragment:${Versions.MyCoroutineDialogFragment}"),
        MY_KODEIN_EX("com.farmanlab:kodein-extensions:${Versions.MyKodeinExtensions}"),

        // ad
        AD_CONSENT("com.google.android.ads.consent:consent-library:${Versions.AdConsent}"),

        // firebase
        FIREBASE_CORE("com.google.firebase:firebase-core:${Versions.FirebaseCore}"),
        FIREBASE_AD("com.google.firebase:firebase-ads:${Versions.FirebaseAd}"),
        FIREBASE_COMMON_KTX("com.google.firebase:firebase-common-ktx:16.1.0"),
        FIREBASE_REMOTE_CONFIG("com.google.firebase:firebase-config:${Versions.FirebaseRemoteConfig}"),
    }

    enum class AndroidTestImplementation(val value: String) {
        JUNIT("junit:junit:4.12"),
        TEST_EXT("androidx.test.ext:junit:${Versions.TestExt}"),
        TEST_RULES("androidx.test:rules:${Versions.SupportTest}"),
        TEST_RUNNER("androidx.test:runner:${Versions.SupportTest}"),
        ESPRESSO_CORE("androidx.test.espresso:espresso-core:${Versions.Espresso}"),
        ESPRESSO_INTENTS("androidx.test.espresso:espresso-intents:${Versions.Espresso}"),
        ESPRESSO_CONTRIB("androidx.test.espresso:espresso-contrib:${Versions.Espresso}"),
    }

    enum class TestImplementation(val value: String) {
        // Test
        JUNIT("junit:junit:4.12"),
        MOCKITO("org.mockito:mockito-core:${Versions.Mockito}"),
        MOCKTIO_KOTLIN("com.nhaarman:mockito-kotlin:${Versions.MockitoKotlin}"),
        TRUTH("com.google.truth:truth:${Versions.Truth}"),
        ROBORECTRIC("org.robolectric:robolectric:${Versions.Robolectric}"),
        ANDROID_X_TEST_CORE("androidx.test:core:1.0.0")
    }

    enum class Kapt(val value: String) {
        LIFECYCLER_COMPILER("androidx.lifecycle:lifecycle-compiler:${Versions.Lifecycle}"),
        ROOM("androidx.room:room-compiler:${Versions.Room}"),
        MOSHI("com.squareup.moshi:moshi-kotlin-codegen:${Versions.Moshi}"),
        KOTSHI("se.ansman.kotshi:compiler:${Versions.Kotshi}"),
        GLIDE("com.github.bumptech.glide:compiler:${Versions.Glide}"),
    }

    enum class DevImplementation(val value: String) {
        HYPERION_CORE("com.willowtreeapps.hyperion:hyperion-core:${Versions.Hyperion}"),
        HYPERION_ATTR("com.willowtreeapps.hyperion:hyperion-attr:${Versions.Hyperion}"),
        HYPERION_MEASUREMENT("com.willowtreeapps.hyperion:hyperion-measurement:${Versions.Hyperion}"),
        HYPERION_DISK("com.willowtreeapps.hyperion:hyperion-disk:${Versions.Hyperion}"),
        HYPERION_RECORDER("com.willowtreeapps.hyperion:hyperion-recorder:${Versions.Hyperion}"),
        HYPERION_PHOENIX("com.willowtreeapps.hyperion:hyperion-phoenix:${Versions.Hyperion}"),
        HYPERION_SHARED_PREFERENCES("com.willowtreeapps.hyperion:hyperion-shared-preferences:${Versions.Hyperion}"),
        HYPERION_TIMBER("com.willowtreeapps.hyperion:hyperion-timber:${Versions.Hyperion}"),
    }
}
