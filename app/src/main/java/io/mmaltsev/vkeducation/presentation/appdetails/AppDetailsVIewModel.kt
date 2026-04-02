package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppDetailsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var currentAppId: String = ""

    fun loadAppDetails(id: String) {
        currentAppId = id
        observeAppDetails()  // ← подписываемся на изменения
    }

    // Подписка на данные из БД (автоматические обновления)
    private fun observeAppDetails() {
        viewModelScope.launch {
            repository.observeAppDetails(currentAppId)
                .catch { e ->
                    _state.value = AppDetailsState.Error(e.message ?: "Ошибка загрузки")
                }
                .collect { appDetails ->
                    _state.value = AppDetailsState.Content(
                        appDetails = appDetails,
                        descriptionCollapsed = false
                    )
                }
        }
    }

    // Переключение wishlist
    fun toggleWishlist() {
        viewModelScope.launch {
            repository.toggleWishlist(currentAppId)
        }
    }

    // Схлопывание описания (если нужно)
    fun collapseDescription() {
        val currentState = _state.value
        if (currentState is AppDetailsState.Content) {
            _state.value = currentState.copy(
                descriptionCollapsed = !currentState.descriptionCollapsed
            )
        }
    }

    sealed class AppDetailsState {
        object Loading : AppDetailsState()
        data class Content(
            val appDetails: AppDetails,
            val descriptionCollapsed: Boolean
        ) : AppDetailsState()
        data class Error(val message: String) : AppDetailsState()
    }
}