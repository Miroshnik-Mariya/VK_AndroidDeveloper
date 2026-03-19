package io.mmaltsev.vkeducation.domain.repository

import io.mmaltsev.vkeducation.domain.model.App

interface AppRepository {
    suspend fun getApps(): List<App>
}