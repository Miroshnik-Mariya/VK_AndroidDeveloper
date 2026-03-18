package io.mmaltsev.vkeducation.presentation.applist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.mmaltsev.vkeducation.R
import io.mmaltsev.vkeducation.presentation.appdetails.InstallButton
import io.mmaltsev.vkeducation.presentation.theme.VkEducationTheme

@Composable
fun AppListHeader(onLogoClick:()->Unit)
{
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(Color.Blue)
        //                //shape = RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
//            )
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


@Preview
@Composable
private fun Preview() {
    VkEducationTheme {
        AppListHeader (
            onLogoClick = {}
        )
    }
}