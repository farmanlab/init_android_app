package com.farmanlab.init_app.di

import com.farmanlab.init_app.domain.gamedata.GameDataUpdateUseCase
import com.farmanlab.init_app.domain.gamedata.GameDataUpdateUseCaseImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val useCaseModule = Kodein.Module("useCase") {
    bind<GameDataUpdateUseCase>() with singleton {
        GameDataUpdateUseCaseImpl(
            gameDataDataSource = instance(),
            gameDataStatusSource = instance()
        )
    }
}
