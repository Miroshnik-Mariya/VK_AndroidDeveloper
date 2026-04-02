package io.mmaltsev.vkeducation.domain.appdetails

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val repository: AppDetailsRepository
) {
    suspend operator fun invoke(id: String): AppDetails {
        return repository.getAppDetails(id)
    }
}