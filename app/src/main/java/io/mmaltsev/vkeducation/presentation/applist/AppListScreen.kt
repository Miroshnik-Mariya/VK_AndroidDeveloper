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
//import io.mmaltsev.vkeducation.presentation.applist.AppListViewModel
import io.mmaltsev.vkeducation.domain.model.App
import io.mmaltsev.vkeducation.presentation.applist.*
import io.mmaltsev.vkeducation.presentation.applist.components.*


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
            //заголовок
            AppListHeader(onLogoClick = { viewModel.onLogoClicked() })

            //список
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
                        //карточка-строка приложения
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


@Preview(showBackground = true)
@Composable
fun AppListScreenPreview() {
    AppListScreen(navController = rememberNavController())
}


