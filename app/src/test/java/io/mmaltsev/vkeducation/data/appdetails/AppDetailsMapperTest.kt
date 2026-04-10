package io.mmaltsev.vkeducation.data.appdetails

import com.google.common.truth.Truth.assertThat
import io.mmaltsev.vkeducation.domain.appdetails.Category
import org.junit.Before
import org.junit.Test

class AppDetailsMapperTest {

    private lateinit var mapper: AppDetailsMapper

    @Before
    fun setUp() {
        mapper = AppDetailsMapper()
    }

    @Test
    fun toDomainShouldMapDTOToDomainModelCorrectly() {
        val dto = AppDetailsDto(
            id = "123",
            name = "Test App",
            developer = "Test Developer",
            category = Category.GAME,
            ageRating = 12,
            size = 50.5,
            icon = "https://test.com/icon.png",
            screenshots = listOf("screenshot1.png", "screenshot2.png"),
            description = "Test description"
        )

        val result = mapper.toDomain(dto)

        assertThat(result.id).isEqualTo("123")
        assertThat(result.name).isEqualTo("Test App")
        assertThat(result.developer).isEqualTo("Test Developer")
        assertThat(result.ageRating).isEqualTo(12)
        assertThat(result.size).isEqualTo(50.5f)
        assertThat(result.iconUrl).isEqualTo("https://test.com/icon.png")
        assertThat(result.screenshotUrlList).hasSize(2)
        assertThat(result.description).isEqualTo("Test description")
    }

    @Test
    fun toDomainShouldHandleNullScreenshots() {
        val dto = AppDetailsDto(
            id = "123",
            name = "Test App",
            developer = "Test Developer",
            category = Category.PRODUCTIVITY,
            ageRating = 12,
            size = 50.5,
            icon = "https://test.com/icon.png",
            screenshots = null,
            description = "Test description"
        )

        val result = mapper.toDomain(dto)

        assertThat(result.screenshotUrlList).isNull()
    }

    @Test
    fun toDomainShouldConvertSizeStringToFloat() {
        val dto = AppDetailsDto(
            id = "123",
            name = "Test",
            developer = "Dev",
            category = Category.GAME,
            ageRating = 12,
            size = 123.45,
            icon = "url",
            screenshots = null,
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertThat(result.size).isEqualTo(123.45f)
    }

    @Test
    fun toDomainShouldHandleInvalidSizeString() {
        val dto = AppDetailsDto(
            id = "123",
            name = "Test",
            developer = "Dev",
            category = Category.BOOKS,
            ageRating = 12,
            size = 0.0,
            icon = "url",
            screenshots = null,
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertThat(result).isNotNull()
        assertThat(result.id).isEqualTo("123")
    }

    @Test
    fun toDomainShouldHandleUnknownCategory() {
        // Для неизвестной категории - используем существующую
        val dto = AppDetailsDto(
            id = "123",
            name = "Test",
            developer = "Dev",
            category = Category.APP,
            ageRating = 12,
            size = 100.0,
            icon = "url",
            screenshots = null,
            description = "desc"
        )

        val result = mapper.toDomain(dto)

        assertThat(result).isNotNull()
        assertThat(result.id).isEqualTo("123")
    }
}