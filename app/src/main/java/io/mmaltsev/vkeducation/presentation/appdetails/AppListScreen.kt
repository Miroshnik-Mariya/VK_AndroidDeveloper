package com.example.vk.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.mmaltsev.vkeducation.R



data class App(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val iconRes: Int
)

@Composable
fun AppListScreen(navController: NavController) {

    val apps = listOf(
        App(
            1,
            "RuStore",
            "Магазин приложений",
            "Отечественный магазин приложений",
            R.drawable.ic_rustore
        ),
        App(
            id = 2,
            name = "СберБанк Онлайн",
            category = "Финансы",
            description = "Больше чем банк",
            iconRes = R.drawable.sber_logo_eng
        ),
        App(
            id = 3,
            name = "Яндекс.Браузер",
            category = "Инструменты",
            description = "Быстрый и безопасный браузер",
            iconRes = R.drawable.yandex_logo_rus
        ),
        App(
            id = 4,
            name = "Почта Mail.ru",
            category = "Инструменты",
            description = "Почтовый клиент",
            iconRes = R.drawable.mail_ru_logo
        ),
        App(
            id = 5,
            name = "Яндекс Навигатор",
            category = "Транспорт",
            description = "Парковки и заправки",
            iconRes = R.drawable.navigator_yandex
        ),
        App(
            id = 6,
            name = "Minecraft",
            category = "Игры",
            description = "Minecraft",
            iconRes = R.drawable.minecraft
        ),
        App(
            id = 7,
            name = "Grand Theft Auto",
            category = "Игры",
            description = "Grand Theft Auto",
            iconRes = R.drawable.gta5
        )
    )

   Column (
       modifier = Modifier
           .fillMaxWidth()
           .background(Color.Blue)
//           .padding(horizontal = 40.dp)
   ) {

       Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(100.dp)
               //.background(Color(0xFFF5F5F5))  // светло-серый фон
               .padding(20.dp),
           contentAlignment = Alignment.TopStart
       ) {
           Column(
               horizontalAlignment = Alignment.CenterHorizontally
               //verticalArrangement = Alignment.CenterVertically as Arrangement.Vertical
           ) {

               Row(
                   modifier = Modifier
                   .fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,  // Центрирование по вертикали
                   horizontalArrangement = Arrangement.SpaceBetween )
               {
                   Icon(
                       painter = painterResource(id = R.drawable.ic_rustore),
                       contentDescription = "RuStore Logo",
                       modifier = Modifier.size(160.dp),
                       tint = Color.White
                       //horizontalAlignment = Alignment.TopStart
                   )
                   Icon(
                       painter = painterResource(id = R.drawable.icon_tehno),
                       contentDescription = "RuStore Logo",
                       modifier = Modifier.size(30.dp),
                       tint = Color.White,

                   )
               }
               Spacer(modifier = Modifier.height(16.dp))

               // Текст под логотипом
               Text(
                   text = "RuStore",
                   style = MaterialTheme.typography.headlineMedium,
                   color = Color.Black
               )

               Text(
                   text = "Магазин приложений",
                   style = MaterialTheme.typography.bodyLarge,
                   color = Color.Gray
               )
           }
       }

       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               //.background(Color.White)
               .background(MaterialTheme.colorScheme.surface)
               //.roundedCornerShape(topStart = 32.dp,topEnd = 32.dp)
               .clip(RoundedCornerShape(18.dp)),


           contentPadding = PaddingValues(0.dp),

//           verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Composable
fun AppListItem(
    app: App,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
//        shape = RoundedCornerShape(
//            topStart = 12.dp,
//            topEnd = 12.dp
//        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.background(Color.White)
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


