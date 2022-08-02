package com.ellies.motooncontactsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContactsList(contacts: List<Contact>, onEditTaskClicked: (Long) -> Unit) {
    LazyColumn {
        items(contacts.size){
            val contact = contacts[it]
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().clickable {
                    onEditTaskClicked(contact.id)
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    text = contact.name
                )
                Text(if (contact.isFavourite) " ⭐" else "")

            }
        }
    }
}