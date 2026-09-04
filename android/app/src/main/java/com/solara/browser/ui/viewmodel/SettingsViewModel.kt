package com.solara.browser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.solara.browser.SolaraApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as SolaraApp
    private val repo = app.settingsRepository
    private val historyRepo = app.historyRepository

    val isDarkMode: StateFlow<Boolean> = repo.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isDynamicColor: StateFlow<Boolean> = repo.isDynamicColor
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isAdBlockerEnabled: StateFlow<Boolean> = repo.isAdBlockerEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isTrackerBlocking: StateFlow<Boolean> = repo.isTrackerBlocking
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isHttpsOnly: StateFlow<Boolean> = repo.isHttpsOnly
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isCompactMode: StateFlow<Boolean> = repo.isCompactMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isReaderMode: StateFlow<Boolean> = repo.isReaderMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val fontSize: StateFlow<Int> = repo.fontSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 16)

    val isSwipeNavigation: StateFlow<Boolean> = repo.isSwipeNavigation
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isBiometricLock: StateFlow<Boolean> = repo.isBiometricLock
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isClearOnExit: StateFlow<Boolean> = repo.isClearOnExit
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch { repo.setDarkMode(enabled) }
    fun setDynamicColor(enabled: Boolean) = viewModelScope.launch { repo.setDynamicColor(enabled) }
    fun setAdBlocker(enabled: Boolean) = viewModelScope.launch { repo.setAdBlocker(enabled) }
    fun setTrackerBlocking(enabled: Boolean) = viewModelScope.launch { repo.setTrackerBlocking(enabled) }
    fun setHttpsOnly(enabled: Boolean) = viewModelScope.launch { repo.setHttpsOnly(enabled) }
    fun setCompactMode(enabled: Boolean) = viewModelScope.launch { repo.setCompactMode(enabled) }
    fun setReaderMode(enabled: Boolean) = viewModelScope.launch { repo.setReaderMode(enabled) }
    fun setFontSize(size: Int) = viewModelScope.launch { repo.setFontSize(size) }
    fun setSwipeNavigation(enabled: Boolean) = viewModelScope.launch { repo.setSwipeNavigation(enabled) }
    fun setBiometricLock(enabled: Boolean) = viewModelScope.launch { repo.setBiometricLock(enabled) }
    fun setClearOnExit(enabled: Boolean) = viewModelScope.launch { repo.setClearOnExit(enabled) }

    fun clearHistoryLastHour() = viewModelScope.launch { historyRepo.clearLastHour() }
    fun clearHistoryLastDay() = viewModelScope.launch { historyRepo.clearLastDay() }
    fun clearHistoryLastWeek() = viewModelScope.launch { historyRepo.clearLastWeek() }
    fun clearHistoryAll() = viewModelScope.launch { historyRepo.clearAll() }
}
