package io.mmaltsev.vkeducation.data.applist

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AppListRepositoryImplTest {

    private lateinit var repository: AppListRepositoryImpl
    private val api: AppListApi = mockk()
    private val mapper: AppListMapper = mockk()

    @Before
    fun setUp() {
        repository = AppListRepositoryImpl(api, mapper)
    }

    @Test
    fun getAppsShouldReturnListOfAppsFromAPI() = runBlocking {
        // Given
        val dtoList = listOf(
            AppListDto("1", "App1", "Cat1", "Desc1", "url1"),
            AppListDto("2", "App2", "Cat2", "Desc2", "url2")
        )
        val domainList = listOf(
            io.mmaltsev.vkeducation.domain.model.App("1", "App1", "Cat1", "Desc1", "url1"),
            io.mmaltsev.vkeducation.domain.model.App("2", "App2", "Cat2", "Desc2", "url2")
        )

        coEvery { api.getCatalog() } returns dtoList
        coEvery { mapper.toDomainList(dtoList) } returns domainList

        // When
        val result = repository.getApps()

        // Then
        assertThat(result).isEqualTo(domainList)
        coVerify { api.getCatalog() }
        coVerify { mapper.toDomainList(dtoList) }
    }

    @Test(expected = Exception::class)
    fun getAppsShouldThrowExceptionWhenAPIFails(): Unit = runBlocking {
        // Given
        coEvery { api.getCatalog() } throws Exception("Network error")

        // When & Then
        repository.getApps()
    }

    @Test
    fun getAppsShouldReturnEmptyListWhenAPIReturnsEmptyList() = runBlocking {
        // Given
        coEvery { api.getCatalog() } returns emptyList()
        coEvery { mapper.toDomainList(emptyList()) } returns emptyList()

        // When
        val result = repository.getApps()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun getAppsShouldHandleLargeList() = runBlocking {
        // Given
        val dtoList = (1..100).map {
            AppListDto("$it", "App$it", "Cat", "Desc", "url")
        }
        val domainList = (1..100).map {
            io.mmaltsev.vkeducation.domain.model.App("$it", "App$it", "Cat", "Desc", "url")
        }

        coEvery { api.getCatalog() } returns dtoList
        coEvery { mapper.toDomainList(dtoList) } returns domainList

        // When
        val result = repository.getApps()

        // Then
        assertThat(result).hasSize(100)
    }

    @Test
    fun getAppsShouldPreserveOrder() = runBlocking {
        // Given
        val dtoList = listOf(
            AppListDto("1", "App1", "Cat1", "Desc1", "url1"),
            AppListDto("2", "App2", "Cat2", "Desc2", "url2"),
            AppListDto("3", "App3", "Cat3", "Desc3", "url3")
        )
        val domainList = listOf(
            io.mmaltsev.vkeducation.domain.model.App("1", "App1", "Cat1", "Desc1", "url1"),
            io.mmaltsev.vkeducation.domain.model.App("2", "App2", "Cat2", "Desc2", "url2"),
            io.mmaltsev.vkeducation.domain.model.App("3", "App3", "Cat3", "Desc3", "url3")
        )

        coEvery { api.getCatalog() } returns dtoList
        coEvery { mapper.toDomainList(dtoList) } returns domainList

        // When
        val result = repository.getApps()

        // Then
        assertThat(result[0].id).isEqualTo("1")
        assertThat(result[1].id).isEqualTo("2")
        assertThat(result[2].id).isEqualTo("3")
    }
}