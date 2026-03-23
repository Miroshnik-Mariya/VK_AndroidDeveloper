package io.mmaltsev.vkeducation.data.applist

import io.mmaltsev.vkeducation.R
import io.mmaltsev.vkeducation.domain.model.App
import io.mmaltsev.vkeducation.domain.repository.AppListRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppListRepositoryImpl @Inject constructor(
    private val mapper: AppListMapper
) : AppListRepository {

    private val hardcodedApps = listOf(
        AppListDto(
            id = 1,
            name = "RuStore",
            category = "Магазин приложений",
            description = "Отечественный магазин приложений",
            iconRes = R.drawable.ic_rustore
        ),
        AppListDto(
            id = 2,
            name = "СберБанк Онлайн",
            category = "Финансы",
            description = "Больше чем банк",
            iconRes = R.drawable.sber_logo_eng
        ),
        AppListDto(
            id = 3,
            name = "Яндекс.Браузер",
            category = "Инструменты",
            description = "Быстрый и безопасный браузер",
            iconRes = R.drawable.yandex_logo_rus
        ),
        AppListDto(
            id = 4,
            name = "Почта Mail.ru",
            category = "Инструменты",
            description = "Почтовый клиент",
            iconRes = R.drawable.mail_ru_logo
        ),
        AppListDto(
            id = 5,
            name = "Яндекс Навигатор",
            category = "Транспорт",
            description = "Парковки и заправки",
            iconRes = R.drawable.navigator_yandex
        ),
        AppListDto(
            id = 6,
            name = "Minecraft",
            category = "Игры",
            description = "Minecraft",
            iconRes = R.drawable.minecraft
        ),
        AppListDto(
            id = 7,
            name = "Grand Theft Auto",
            category = "Игры",
            description = "Grand Theft Auto",
            iconRes = R.drawable.gta5
        ),
        AppListDto(
            id = 1,
            name = "RuStore",
            category = "Магазин приложений",
            description = "Отечественный магазин приложений",
            iconRes = R.drawable.ic_rustore
            // iconUrl НЕ УКАЗЫВАЕМ - он опциональный (String? = null)
        ),
        AppListDto(
            id = 2,
            name = "СберБанк Онлайн",
            category = "Финансы",
            description = "Больше чем банк",
            iconRes = R.drawable.sber_logo_eng
        ),
        AppListDto(
            id = 3,
            name = "Яндекс.Браузер",
            category = "Инструменты",
            description = "Быстрый и безопасный браузер",
            iconRes = R.drawable.yandex_logo_rus
        ),
        AppListDto(
            id = 4,
            name = "Почта Mail.ru",
            category = "Инструменты",
            description = "Почтовый клиент",
            iconRes = R.drawable.mail_ru_logo
        ),
        AppListDto(
            id = 5,
            name = "Яндекс Навигатор",
            category = "Транспорт",
            description = "Парковки и заправки",
            iconRes = R.drawable.navigator_yandex
        ),
        AppListDto(
            id = 6,
            name = "Minecraft",
            category = "Игры",
            description = "Minecraft",
            iconRes = R.drawable.minecraft
        ),
        AppListDto(
            id = 7,
            name = "Grand Theft Auto",
            category = "Игры",
            description = "Grand Theft Auto",
            iconRes = R.drawable.gta5
        )
    )

    override suspend fun getApps(): List<App> {
        delay(1000)
        return mapper.toDomainList(hardcodedApps)
    }

}