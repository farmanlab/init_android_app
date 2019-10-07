package com.farmanlab.init_app.di

import com.farmanlab.init_app.AppInitializer
import com.farmanlab.init_app.AppInitializerImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val applicationModule = Kodein.Module("application_module", false) {
    import(networkModule)
    import(adManagerModule)
    import(dataSourceModule)
    import(useCaseModule)
    bind<AppInitializer>() with singleton {
        AppInitializerImpl(
            context = instance(),
            gameDataStatusDataSource = instance(),
            userDataSource = instance()
        )
    }
}

