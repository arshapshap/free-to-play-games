package com.example.games.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.games.R
import com.example.games.di.DaggerAppComponent
import com.example.games.presentation.ui.theme.GamesTheme
import com.example.games.presentation.ui.theme.Typography

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = DaggerAppComponent.builder().build()

        setContent {
            GamesTheme { // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        MainNavHost(component)
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(
) {
    androidx.compose.material.TopAppBar {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp
                ),
            text = stringResource(id = R.string.app_name), style = Typography.h5
        )
    }
}