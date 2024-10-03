package com.example.wizardsapplication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.wizardsapplication.data.model.Elixir
import com.example.wizardsapplication.data.model.WizardResponseItem

@Composable
fun WizardDetailsScreen(wizard: WizardResponseItem, onSelectElixir: (Elixir) -> Unit) {

    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = " ${wizard.firstName ?: ""} ${wizard.lastName ?: ""}",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 18.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )

        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .padding(top = 16.dp)) {

            item {
                Text(
                    text = "Elixir",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 18.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )

            }

            items(wizard.elixirs ?: listOf()) { elixir ->

                elixir.name?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .clickable {
                                onSelectElixir.invoke(elixir)
                            },
                        overflow = TextOverflow.Clip
                    )
                }
                HorizontalDivider()
            }
        }
    }
}