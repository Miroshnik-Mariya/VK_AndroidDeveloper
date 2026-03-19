package io.mmaltsev.vkeducation.data.repository

import io.mmaltsev.vkeducation.data.applist.AppListDto
import io.mmaltsev.vkeducation.data.applist.AppListMapper
import io.mmaltsev.vkeducation.domain.model.App
import io.mmaltsev.vkeducation.domain.repository.AppRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val mapper: AppListMapper
) : AppRepository {

    // ХАРДКОД ДАННЫЕ в виде DTO
    private val hardcodedApps = listOf(
        AppListDto(
            id = 1,
            name = "RuStore",
            category = "Магазин приложений",
            description = "Отечественный магазин приложений",
            iconRes = io.mmaltsev.vkeducation.R.drawable.ic_rustore

        ),
        AppListDto(
            id = 2,
            name = "СберБанк Онлайн",
            category = "Финансы",
            description = "Больше чем банк",
            iconRes = io.mmaltsev.vkeducation.R.drawable.sber_logo_eng
        ),
        AppListDto(
            id = 3,
            name = "Яндекс.Браузер",
            category = "Инструменты",
            description = "Быстрый и безопасный браузер",
            iconRes = io.mmaltsev.vkeducation.R.drawable.yandex_logo_rus
        ),
        AppListDto(
            id = 4,
            name = "Почта Mail.ru",
            category = "Инструменты",
            description = "Почтовый клиент",
            iconRes = io.mmaltsev.vkeducation.R.drawable.mail_ru_logo
        ),
        AppListDto(
            id = 5,
            name = "Яндекс Навигатор",
            category = "Транспорт",
            description = "Парковки и заправки",
            iconRes = io.mmaltsev.vkeducation.R.drawable.navigator_yandex
        ),
        AppListDto(
            id = 6,
            name = "Minecraft",
            category = "Игры",
            description = "Minecraft",
            iconRes = io.mmaltsev.vkeducation.R.drawable.minecraft
        ),
        AppListDto(
            id = 7,
            name = "Grand Theft Auto",
            category = "Игры",
            description = "Grand Theft Auto",
            iconRes = io.mmaltsev.vkeducation.R.drawable.gta5
        )
    )

    override suspend fun getApps(): List<App> {
        delay(1000)
        return mapper.toDomainList(hardcodedApps)
    }
}