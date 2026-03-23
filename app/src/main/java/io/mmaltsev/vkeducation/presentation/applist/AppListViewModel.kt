package io.mmaltsev.vkeducation.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mmaltsev.vkeducation.domain.model.App
import io.mmaltsev.vkeducation.domain.repository.AppListRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppListRepository 
) : ViewModel() {

    // Состояние списка приложений
    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    // Состояние загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // События для Snackbar
    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents = _snackbarEvents.asSharedFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apps = repository.getApps()
                _apps.value = apps
            } catch (e: Exception) {
                _snackbarEvents.emit("Ошибка загрузки: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onLogoClicked() {
        viewModelScope.launch {
            _snackbarEvents.emit("Привет! Не нажимай на меня;)")
        }
    }

    fun onAppClicked(appId: Int) {
        // Здесь будет навигация
    }
}