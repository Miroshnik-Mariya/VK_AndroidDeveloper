package com.example.vk.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import io.mmaltsev.vkeducation.R
import io.mmaltsev.vkeducation.presentation.appdetails.AppListViewModel


data class App(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val iconRes: Int
)

@Composable
fun AppListScreen(
    navController: NavController,
    viewModel: AppListViewModel = viewModel()) {
    val apps by viewModel.apps.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Blue)
        )

        {
            AppListHeader(onLogoClick = { viewModel.onLogoClicked() })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentPadding = PaddingValues(0.dp)

                ) {
                    items(apps) { app ->

                        AppListItem(
                            app = app,
                            onClick = {
                                // Переход на карточку приложения
                                navController.navigate("app_detail/${app.id}")
                            }
                        )
                    }

                }
            }
        }
    }
}

    @Composable
    fun AppListHeader(onLogoClick:()->Unit)
    {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable{onLogoClick()}
            .padding(20.dp)
        )
        {
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rustore),
                    contentDescription = "Logo",
                    modifier = Modifier.size(160.dp),
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(id = R.drawable.icon_tehno),
                    contentDescription = "Settings",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }
    }

@Composable
fun AppListItem(
    app: App,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = app.iconRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = app.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = app.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    AppListScreen(navController = rememberNavController())
}


