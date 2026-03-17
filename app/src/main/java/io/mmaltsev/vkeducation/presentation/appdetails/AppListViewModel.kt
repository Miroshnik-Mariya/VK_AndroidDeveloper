package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk.presentation.App
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import io.mmaltsev.vkeducation.R
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel : ViewModel() {

    //Состояние списка приложений
    private val _apps = MutableStateFlow<List<App>>(emptyList())
    val apps: StateFlow<List<App>> = _apps.asStateFlow()

    //События для Snackbar
    //SharedFlow, чтобы не повторялись при перевороте
    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents = _snackbarEvents.asSharedFlow()

    init {
        loadApps()
    }

    //Список приложений
    private fun loadApps() {
        _apps.value = listOf()
    }

    fun onLogoClicked() {
        viewModelScope.launch {
            _snackbarEvents.emit("Логотип нажат!")
        }
    }

    fun onAppClicked(appId: Int) {
        //Здесь будет навигация
    }
}