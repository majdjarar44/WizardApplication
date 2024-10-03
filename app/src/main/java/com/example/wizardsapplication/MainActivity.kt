package com.example.wizardsapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wizardsapplication.presentation.ElixirDetailsScreen
import com.example.wizardsapplication.presentation.Screen
import com.example.wizardsapplication.presentation.WizardDetailsScreen
import com.example.wizardsapplication.presentation.WizardListScreen
import com.example.wizardsapplication.presentation.WizardViewModel
import com.example.wizardsapplication.ui.theme.WizardsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: WizardViewModel = hiltViewModel()
            WizardsApplicationTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) { innerPadding ->
                    BackHandler {
                        viewModel.onBackClicked {
                            this@MainActivity.finish()
                        }
                    }
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .padding(innerPadding)
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(innerPadding)
                                .background(Color.White)
                        ) {

                            Row(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.ic_back_button),
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .weight(.1f)
                                        .align(Alignment.CenterVertically)
                                        .padding(10.dp)
                                        .clickable {
                                            viewModel.onBackClicked() {
                                                this@MainActivity.finish()
                                            }
                                        }
                                )
                                Spacer(modifier = Modifier.weight(.8f))
                            }
                            Divider()
                            Spacer(modifier = Modifier.padding(8.dp))
                            MainScreen(viewModel = viewModel)
                        }
                        if (viewModel.uiState.collectAsState().value.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: WizardViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    when (uiState.currentScreen) {

        Screen.WizardList ->
            WizardListScreen(uiState.wizardList ?: listOf(), { wizard ->
                viewModel.selectWizard(wizard)
            }, {
                viewModel.toggleFavorite(it.id)
            })

        Screen.WizardDetails -> uiState.wizardSelectedItem?.let {
            WizardDetailsScreen(it) {
                viewModel.selectElixir(it)
            }
        }

        Screen.ElixirDetails -> uiState.elixirDetailItem?.let { ElixirDetailsScreen(it) }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WizardsApplicationTheme {

    }
}