package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import io.mmaltsev.vkeducation.domain.appdetails.AppDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    private val repository: AppDetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    private var currentAppId: String = ""

    fun loadAppDetails(id: String) {
        currentAppId = id
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            try {
                val details = repository.getAppDetails(id)
                _state.value = AppDetailsState.Content(
                    appDetails = details,
                    descriptionCollapsed = false
                )
            } catch (e: Exception) {
                _state.value = AppDetailsState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            try {
                // 1. Обновляем статус в БД
                repository.toggleWishlist(currentAppId)

                // 2. Перезагружаем данные из БД
                val updatedDetails = repository.getAppDetails(currentAppId)

                // 3. Обновляем UI
                val currentState = _state.value
                if (currentState is AppDetailsState.Content) {
                    _state.value = currentState.copy(appDetails = updatedDetails)
                }
            } catch (e: Exception) {
                android.util.Log.e("AppDetailsVM", "Error toggling wishlist", e)
            }
        }
    }

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