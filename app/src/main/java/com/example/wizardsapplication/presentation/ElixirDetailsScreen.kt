package com.example.wizardsapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse


@Composable
fun ElixirDetailsScreen(elixir: ElixirResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
    ) {

        elixir.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Text(
            text = "Effect: ${elixir.effect ?: ""}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Characteristics: ${elixir.characteristics ?: ""}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        elixir?.sideEffects?.let {
            Text(
                text = "Side Effects: ${elixir.sideEffects}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        elixir?.difficulty?.let {
            Text(
                text = "Difficulty: ${elixir.difficulty}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        elixir.manufacturer?.let {
            Text(
                text = "Manufacturer: $it",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        HorizontalDivider()

        if (!elixir.ingredients.isNullOrEmpty()) {
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                items(elixir.ingredients ?: listOf()) { ingredient ->
                    Text(
                        text = "- ${ingredient.name ?: ""}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            HorizontalDivider()
        }

        if(!elixir?.inventors.isNullOrEmpty()) {
            Text(
                text = "Inventors:",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(elixir.inventors ?: listOf()) { inventor ->
                    Text(
                        text = "- ${inventor.firstName ?: ""} ${inventor.lastName ?: ""}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }
        }

    }
}


