package io.mmaltsev.vkeducation.data.appdetails

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsDao
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntity
import io.mmaltsev.vkeducation.data.appdetails.local.AppDetailsEntityMapper
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails
import org.junit.Before
import org.junit.Test

class AppDetailsRepositoryImplTest {

    private lateinit var repository: AppDetailsRepositoryImpl
    private val appApi: AppApi = mockk()
    private val dao: AppDetailsDao = mockk()
    private val appDetailsMapper: AppDetailsMapper = mockk()
    private val entityMapper: AppDetailsEntityMapper = mockk()

    @Before
    fun setUp() {
        repository = AppDetailsRepositoryImpl(
            appApi, dao, appDetailsMapper, entityMapper
        )
    }

    @Test
    fun getAppDetailsShouldReturnCachedEntityWhenExistsInDB() = runBlocking {
        // Given
        val appId = "123"
        val entity = mockk<AppDetailsEntity>()
        val expectedDomain = mockk<AppDetails>()

        coEvery { dao.getAppDetails(appId) } returns entity
        coEvery { entityMapper.toDomain(entity) } returns expectedDomain

        // When
        val result = repository.getAppDetails(appId)

        // Then
        assertThat(result).isEqualTo(expectedDomain)
        coVerify(exactly = 0) { appApi.getAppDetails(any()) }
    }

    @Test
    fun getAppDetailsShouldFetchFromNetworkWhenNotInDB() = runBlocking {
        // Given
        val appId = "123"
        val dto = mockk<AppDetailsDto>()
        val domain = mockk<AppDetails>()
        val entity = mockk<AppDetailsEntity>()

        coEvery { dao.getAppDetails(appId) } returns null
        coEvery { appApi.getAppDetails(appId) } returns dto
        coEvery { appDetailsMapper.toDomain(dto) } returns domain
        coEvery { entityMapper.toEntity(domain) } returns entity
        coEvery { dao.insertAppDetails(entity) } returns Unit

        // When
        val result = repository.getAppDetails(appId)

        // Then
        assertThat(result).isEqualTo(domain)
        coVerify { appApi.getAppDetails(appId) }
        coVerify { dao.insertAppDetails(entity) }
    }

    @Test
    fun toggleWishlistShouldUpdateStatusCorrectlyWhenEntityExists() = runBlocking {
        // Given
        val appId = "123"
        val entity = mockk<AppDetailsEntity>()
        coEvery { dao.getAppDetails(appId) } returns entity
        coEvery { entity.isInWishlist } returns false
        coEvery { dao.updateWishlistStatus(appId, true) } returns Unit

        // When
        repository.toggleWishlist(appId)

        // Then
        coVerify { dao.updateWishlistStatus(appId, true) }
    }

    @Test
    fun toggleWishlistShouldDoNothingWhenEntityDoesNotExist() = runBlocking {
        // Given
        val appId = "123"
        coEvery { dao.getAppDetails(appId) } returns null

        // When
        repository.toggleWishlist(appId)

        // Then
        coVerify(exactly = 0) { dao.updateWishlistStatus(any(), any()) }
    }

    @Test
    fun toggleWishlistShouldToggleFromTrueToFalse() = runBlocking {
        // Given
        val appId = "123"
        val entity = mockk<AppDetailsEntity>()
        coEvery { dao.getAppDetails(appId) } returns entity
        coEvery { entity.isInWishlist } returns true
        coEvery { dao.updateWishlistStatus(appId, false) } returns Unit

        // When
        repository.toggleWishlist(appId)

        // Then
        coVerify { dao.updateWishlistStatus(appId, false) }
    }
}