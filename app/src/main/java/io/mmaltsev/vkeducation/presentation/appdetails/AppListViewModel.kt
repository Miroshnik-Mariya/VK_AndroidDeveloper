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
        _apps.value = listOf(
            App(
                1,
                "RuStore",
                "Магазин приложений",
                "Отечественный магазин приложений",
                R.drawable.ic_rustore
            ),
            App(
                id = 2,
                name = "СберБанк Онлайн",
                category = "Финансы",
                description = "Больше чем банк",
                iconRes = R.drawable.sber_logo_eng
            ),
            App(
                id = 3,
                name = "Яндекс.Браузер",
                category = "Инструменты",
                description = "Быстрый и безопасный браузер",
                iconRes = R.drawable.yandex_logo_rus
            ),
            App(
                id = 4,
                name = "Почта Mail.ru",
                category = "Инструменты",
                description = "Почтовый клиент",
                iconRes = R.drawable.mail_ru_logo
            ),
            App(
                id = 5,
                name = "Яндекс Навигатор",
                category = "Транспорт",
                description = "Парковки и заправки",
                iconRes = R.drawable.navigator_yandex
            ),
            App(
                id = 6,
                name = "Minecraft",
                category = "Игры",
                description = "Minecraft",
                iconRes = R.drawable.minecraft
            ),
            App(
                id = 7,
                name = "Grand Theft Auto",
                category = "Игры",
                description = "Grand Theft Auto",
                iconRes = R.drawable.gta5
            )
        )
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