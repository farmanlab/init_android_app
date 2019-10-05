package com.farmanlab.init_app.di

import com.farmanlab.init_app.data.gamedata.GameDataDataSource
import com.farmanlab.init_app.data.gamedata.RetrofitGameDataDataSource
import com.farmanlab.init_app.data.gamedatastatus.GameDataStatusDataSource
import com.farmanlab.init_app.data.gamedatastatus.SharedPreferenceGameDataStatusDataSource
import com.farmanlab.init_app.data.requestreview.RequestReviewDataSource
import com.farmanlab.init_app.data.requestreview.SharedPreferenceRequestReviewDataSource
import com.farmanlab.init_app.data.settings.LocalSettingsDataSource
import com.farmanlab.init_app.data.settings.SettingsDataSource
import com.farmanlab.init_app.domain.gamedata.GameDataUpdateUseCase
import com.farmanlab.init_app.domain.gamedata.GameDataUpdateUseCaseImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val useCaseSourceModule = Kodein.Module("useCase") {
    bind<GameDataUpdateUseCase>() with singleton {
        GameDataUpdateUseCaseImpl(
            gameDataDataSource = instance(),
            gameDataStatusSource = instance()
        )
    }
}
