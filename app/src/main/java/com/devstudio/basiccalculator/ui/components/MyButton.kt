package com.devstudio.basiccalculator.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devstudio.basiccalculator.ui.theme.primaryButton
import com.devstudio.basiccalculator.ui.theme.secondaryButton
import com.devstudio.basiccalculator.ui.theme.specialButton

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    onClicked: (String) -> Unit = {},
    text: String = "9",
    color: Color = Color.Unspecified,
    fontSize: Int = 18
) {

    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp),
//        color = color,
//        tonalElevation = 8.dp,
//        shadowElevation = 8.dp,
        onClick = {
            onClicked(text)
        },
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        val primaryButtonImp = primaryButton
        val secondaryButtonImp = secondaryButton
        val check1 = color == primaryButtonImp
        val check2 = color == secondaryButtonImp
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = text,
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else if (check1 || check2) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
