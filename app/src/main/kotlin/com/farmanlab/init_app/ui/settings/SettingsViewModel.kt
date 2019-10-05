package com.farmanlab.init_app.ui.settings

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmanlab.init_app.data.settings.SettingsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import postValueIfNew
import java.util.*

class SettingsViewModel(
    private val settingsDataSource: SettingsDataSource
) : ViewModel() {
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _settings: MutableLiveData<List<SettingsContract.Setting>> = MutableLiveData()
    val settings: LiveData<List<SettingsContract.Setting>>
        get() = _settings

    fun onFetchSettings() {
        _showLoading.postValueIfNew(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    settingsDataSource.fetchAll()
                }.fold(
                    onSuccess = { result ->
                        _settings.postValue(result.mapNotNull { it.convert() })
                        _showLoading.postValueIfNew(false)
                    },
                    onFailure = { _showLoading.postValueIfNew(false) }
                )
            }
        }
    }

    // region convert
    private fun SettingsDataSource.Setting.convert(): SettingsContract.Setting? =
        SettingsContract.Setting(
            id = id,
            title = title,
            description = description
        )
    // endregion
}
