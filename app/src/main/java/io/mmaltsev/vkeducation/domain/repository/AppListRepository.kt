package io.mmaltsev.vkeducation.domain.repository

import io.mmaltsev.vkeducation.domain.model.App

interface AppListRepository {
    suspend fun getApps(): List<App>
}