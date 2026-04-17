package io.mmaltsev.vkeducation.data.applist

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class AppListMapperTest {

    private lateinit var mapper: AppListMapper

    @Before
    fun setUp() {
        mapper = AppListMapper()
    }

    @Test
    fun toDomainShouldMapDTOToDomainModelCorrectly() {
        // Given
        val dto = AppListDto(
            id = "123",
            name = "Test App",
            category = "Test Category",
            description = "Test Description",
            iconUrl = "https://test.com/icon.png"
        )

        // When
        val result = mapper.toDomain(dto)

        // Then
        assertThat(result.id).isEqualTo("123")
        assertThat(result.name).isEqualTo("Test App")
        assertThat(result.category).isEqualTo("Test Category")
        assertThat(result.description).isEqualTo("Test Description")
        assertThat(result.iconUrl).isEqualTo("https://test.com/icon.png")
    }

    @Test
    fun toDomainShouldHandleEmptyIconUrl() {
        // Given
        val dto = AppListDto(
            id = "123",
            name = "Test App",
            category = "Test Category",
            description = "Test Description",
            iconUrl = ""
        )

        // When
        val result = mapper.toDomain(dto)

        // Then
        assertThat(result.iconUrl).isEmpty()
    }

    @Test
    fun toDomainShouldHandleNullIconUrl() {
        // Given
        val dto = AppListDto(
            id = "123",
            name = "Test App",
            category = "Test Category",
            description = "Test Description",
            iconUrl = null
        )

        // When
        val result = mapper.toDomain(dto)

        // Then
        assertThat(result.iconUrl).isNotNull()
    }

    @Test
    fun toDomainListShouldMapEmptyListCorrectly() {
        // Given
        val dtoList = emptyList<AppListDto>()

        // When
        val result = mapper.toDomainList(dtoList)

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun toDomainListShouldMapMultipleDTOsCorrectly() {
        // Given
        val dtoList = listOf(
            AppListDto("1", "App1", "Cat1", "Desc1", "url1"),
            AppListDto("2", "App2", "Cat2", "Desc2", "url2")
        )

        // When
        val result = mapper.toDomainList(dtoList)

        // Then
        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("App1")
        assertThat(result[1].name).isEqualTo("App2")
    }
}