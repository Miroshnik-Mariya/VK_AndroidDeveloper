package io.mmaltsev.vkeducation.presentation.appdetails

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
    private val repository: AppDetailsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    fun loadAppDetails(id: String) {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            try {
                val details = repository.getAppDetails(id)  // Просто получаем данные
                _state.value = AppDetailsState.Success(details)
            } catch (e: Exception) {
                _state.value = AppDetailsState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    sealed class AppDetailsState {
        object Loading : AppDetailsState()
        data class Success(val details: AppDetails) : AppDetailsState()
        data class Error(val message: String) : AppDetailsState()
    }
}