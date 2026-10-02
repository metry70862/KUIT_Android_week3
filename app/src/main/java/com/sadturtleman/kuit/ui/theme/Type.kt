package com.sadturtleman.kuit.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sadturtleman.kuit.R

val NotoSansKr = FontFamily(
    Font(
        resId = R.font.noto_sans_kr_regular,
        weight = FontWeight.Normal
    )
)

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = NotoSansKr,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)