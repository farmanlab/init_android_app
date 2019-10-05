package com.farmanlab.init_app.domain.gamedata

import com.farmanlab.init_app.data.gamedatastatus.GameDataStatus
import com.farmanlab.init_app.data.gamedata.GameDataDataSource
import com.farmanlab.init_app.data.gamedatastatus.GameDataStatusDataSource
import org.threeten.bp.LocalDateTime

class GameDataUpdateUseCaseImpl(
    private val gameDataDataSource: GameDataDataSource,
    private val gameDataStatusSource: GameDataStatusDataSource
) : GameDataUpdateUseCase {
    override suspend fun updateData(request: GameDataUpdateUseCase.RequestParams) {
        val userData = gameDataStatusSource.fetchStatus()

        val data = gameDataDataSource.fetch(
            createDataSourceRequestParams(request, userData.hashCommits)
        )
        data.hashCommits?.also { gameDataStatusSource.setHashCommits(it.convert()) }
        gameDataStatusSource.setDataInitialized()
        gameDataStatusSource.setLastUpdatedDateTime(LocalDateTime.now())
    }

    private fun createDataSourceRequestParams(
        params: GameDataUpdateUseCase.RequestParams,
        hashCommits: GameDataStatus.HashCommits
    ): GameDataDataSource.RequestParams =
        GameDataDataSource.RequestParams(apiToken = params.apiToken)

    private fun GameDataDataSource.GameData.HashCommits.convert(): GameDataStatus.HashCommits =
        GameDataStatus.HashCommits()
}
