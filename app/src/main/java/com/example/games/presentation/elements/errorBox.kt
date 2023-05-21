package com.example.games.presentation.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.games.R
import com.example.games.presentation.ui.theme.Typography

@Composable
fun ErrorBox() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_puzzle),
                contentDescription = null,
                tint = Color.Gray,
            )
            Text(
                text = stringResource(id = R.string.something_went_wrong),
                style = Typography.overline,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPreview() {
    ErrorBox()
}