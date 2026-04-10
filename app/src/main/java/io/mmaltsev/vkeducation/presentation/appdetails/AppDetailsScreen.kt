package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import io.mmaltsev.vkeducation.domain.appdetails.AppDetails



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    navController: NavController,
    appId: String,
    viewModel: AppDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(appId) {
        viewModel.loadAppDetails(appId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали приложения") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    // ← Кнопка Wishlist
                    when (state) {
                        is AppDetailsViewModel.AppDetailsState.Content -> {
                            val isInWishlist = (state as AppDetailsViewModel.AppDetailsState.Content)
                                .appDetails.isInWishlist
                            IconButton(onClick = { viewModel.toggleWishlist() }) {
                                Icon(
                                    imageVector = if (isInWishlist) Icons.Filled.Favorite
                                    else Icons.Filled.FavoriteBorder,
                                    contentDescription = "В избранное",
                                    tint = if (isInWishlist) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        else -> {}
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is AppDetailsViewModel.AppDetailsState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is AppDetailsViewModel.AppDetailsState.Content -> {
                    val content = state as AppDetailsViewModel.AppDetailsState.Content
                    DetailsContent(
                        details = content.appDetails,
                        isDescriptionCollapsed = content.descriptionCollapsed,
                        onReadMoreClick = { viewModel.collapseDescription() }
                    )
                }
                is AppDetailsViewModel.AppDetailsState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = (state as AppDetailsViewModel.AppDetailsState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadAppDetails(appId) }) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }
    }
}

@Composable

fun DetailsContent(
    details: AppDetails,
    isDescriptionCollapsed: Boolean,
    onReadMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = details.iconUrl,
                contentDescription = "App icon",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(
                    text = details.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = details.developer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (details.isInWishlist) "В избранном" else "Добавить в избранное",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow("Категория", details.category)
                InfoRow("Возрастной рейтинг", "${details.ageRating}+")
                InfoRow("Размер", "${details.size} MB")
            }
        }

        // Description
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Описание",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = details.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isDescriptionCollapsed) 3 else Int.MAX_VALUE
                )
                if (details.description.length > 200) {
                    TextButton(onClick = onReadMoreClick) {
                        Text(if (isDescriptionCollapsed) "Читать далее" else "Свернуть")
                    }
                }
            }
        }

        // Screenshots
        if (!details.screenshotUrlList.isNullOrEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Скриншоты",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(details.screenshotUrlList) { screenshot ->
                            AsyncImage(
                                model = screenshot,
                                contentDescription = "Screenshot",
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(120.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}