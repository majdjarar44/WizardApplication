package com.example.wizardsapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsapplication.R


@Composable
fun WizardListScreen(
    wizards: List<WizardResponseItem>,
    onSelect: (WizardResponseItem) -> Unit,
    onFavoriteClick: (WizardResponseItem) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
        items(wizards) { wizard ->
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .padding(bottom = 8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    ),
            ) {
                Text(
                    text = "${wizard.firstName ?: ""} ${wizard.lastName ?: ""}",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(.8f)
                        .align(Alignment.CenterVertically)
                        .padding(top = 8.dp)
                        .clickable {
                            onSelect.invoke(wizard)
                        },
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                    ),
                )

                Icon(
                    painterResource(id = R.drawable.ic_favorite_button),
                    contentDescription = "Favorite",
                    tint = if(wizard.isFavorite==true) Color.Red else Color(0xffCCCCCC),
                    modifier = Modifier
                        .size(24.dp)
                        .weight(.1f)
                        .align(Alignment.CenterVertically).clickable {
                            onFavoriteClick.invoke(wizard)
                        }
                )
            }

        }

    }
}


@Composable
@Preview
fun WizardListScreenPreview() {
    WizardListScreen(
        listOf(
            WizardResponseItem(
                elixirs = listOf(),
                firstName = "Fred Wasly",
                id = "0",
                lastName = ""
            ),
            WizardResponseItem(
                elixirs = listOf(),
                firstName = "Fred Waslyjgfsdhjgfshdfgshjgdfhjhjghjhjghjjhjhjk",
                id = "1",
                lastName = ""
            ),
            WizardResponseItem(
                elixirs = listOf(),
                firstName = "Fred Wasly",
                id = "3",
                lastName = ""
            )
        ),
        onSelect = { },{}
    )
}