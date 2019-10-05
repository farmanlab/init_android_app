package com.farmanlab.init_app.di

import com.farmanlab.init_app.data.gamedata.GameDataDataSource
import com.farmanlab.init_app.data.gamedata.RetrofitGameDataDataSource
import com.farmanlab.init_app.data.gamedatastatus.GameDataStatusDataSource
import com.farmanlab.init_app.data.gamedatastatus.SharedPreferenceGameDataStatusDataSource
import com.farmanlab.init_app.data.requestreview.RequestReviewDataSource
import com.farmanlab.init_app.data.requestreview.SharedPreferenceRequestReviewDataSource
import com.farmanlab.init_app.data.settings.LocalSettingsDataSource
import com.farmanlab.init_app.data.settings.SettingsDataSource
import com.farmanlab.init_app.data.user.SharedPreferenceUserDataSource
import com.farmanlab.init_app.data.user.UserDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val dataSourceModule = Kodein.Module("dataSource") {
    bind<SettingsDataSource>() with singleton { LocalSettingsDataSource(instance()) }
    bind<GameDataDataSource>() with singleton { RetrofitGameDataDataSource(retrofit = instance()) }
    bind<GameDataStatusDataSource>() with singleton { SharedPreferenceGameDataStatusDataSource(instance()) }
    bind<RequestReviewDataSource>() with singleton { SharedPreferenceRequestReviewDataSource(instance()) }
    bind<UserDataSource>() with singleton { SharedPreferenceUserDataSource(instance()) }
}
