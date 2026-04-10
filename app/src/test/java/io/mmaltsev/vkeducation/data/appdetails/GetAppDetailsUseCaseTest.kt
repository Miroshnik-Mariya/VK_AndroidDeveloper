package io.mmaltsev.vkeducation.domain.appdetails

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAppDetailsUseCaseTest {

    private lateinit var useCase: GetAppDetailsUseCase
    private val repository: AppDetailsRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetAppDetailsUseCase(repository)
    }

    @Test
    fun invokeShouldReturnAppDetailsFromRepository() = runBlocking {
        // Given
        val appId = "123"
        val expectedDetails = mockk<AppDetails>()
        coEvery { repository.getAppDetails(appId) } returns expectedDetails

        // When
        val result = useCase(appId)

        // Then
        assertThat(result).isEqualTo(expectedDetails)
        coVerify { repository.getAppDetails(appId) }
    }

    @Test(expected = Exception::class)
    fun invokeShouldPropagateExceptionWhenRepositoryFails(): Unit = runBlocking {
        // Given
        val appId = "123"
        coEvery { repository.getAppDetails(appId) } throws Exception("Network error")

        // When & Then
        useCase(appId)
    }

    @Test
    fun invokeShouldHandleEmptyId() = runBlocking {
        // Given
        val appId = ""
        val expectedDetails = mockk<AppDetails>()
        coEvery { repository.getAppDetails(appId) } returns expectedDetails

        // When
        val result = useCase(appId)

        // Then
        assertThat(result).isEqualTo(expectedDetails)
        coVerify { repository.getAppDetails(appId) }
    }

    @Test
    fun invokeShouldCallRepositoryWithCorrectIdMultipleTimes() = runBlocking {
        // Given
        val appId1 = "123"
        val appId2 = "456"
        val details1 = mockk<AppDetails>()
        val details2 = mockk<AppDetails>()

        coEvery { repository.getAppDetails(appId1) } returns details1
        coEvery { repository.getAppDetails(appId2) } returns details2

        // When
        val result1 = useCase(appId1)
        val result2 = useCase(appId2)

        // Then
        assertThat(result1).isEqualTo(details1)
        assertThat(result2).isEqualTo(details2)
        coVerify { repository.getAppDetails(appId1) }
        coVerify { repository.getAppDetails(appId2) }
    }

    @Test
    fun invokeShouldHandleSpecialCharactersInId() = runBlocking {
        // Given
        val appId = "app-123_uuid"
        val expectedDetails = mockk<AppDetails>()
        coEvery { repository.getAppDetails(appId) } returns expectedDetails

        // When
        val result = useCase(appId)

        // Then
        assertThat(result).isEqualTo(expectedDetails)
        coVerify { repository.getAppDetails(appId) }
    }
}