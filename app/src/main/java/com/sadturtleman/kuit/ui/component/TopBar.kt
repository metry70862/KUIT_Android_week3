package com.sadturtleman.kuit.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF3EDF7)
        ),
        navigationIcon = {
            Text(
                text = "←",
                fontSize = 26.sp,
                modifier = Modifier
                    .clickable(onClick = onBack)
                    .padding(top = 5.dp, bottom = 12.dp, start = 20.dp, end = 18.dp)

            )
        },
        title = {
            Text(
                text = title,
                style = Typography.bodyMedium,
                fontSize = 22.sp,
                color = Color(0xFF1D1B20)
            )

        }
    )
}

@Preview
@Composable
private fun TopBarPrev() {
    TopBar("책 상세", onBack = {})
}