package com.ellies.motooncontactsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.ellies.motooncontactsapp.ui.theme.MotoonContactsAppTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contactDao = ContactDatabase.getInstance(applicationContext).contactDao
        val vmf = MyViewModelFactory(contactDao)
        setContent {
            MotoonContactsAppTheme(){
                Surface(color = MaterialTheme.colors.background) {
                    MainNavigation(vmf = vmf)
                }
            }
        }
    }

}

@Composable
fun MyScaffold(
    title: String,
    fabIcon: ImageVector? = null,
    onFabClicked: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {TopAppBar(title = {Text(title)}) },
        floatingActionButton = {
            fabIcon?.let {
                FloatingActionButton(onClick = onFabClicked) {
                    Icon(it, "Fab icon")
                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun ContactEditor(contact: Contact, onContactSave: (Contact) -> Unit) {
    Column {
        var name by rememberSaveable { mutableStateOf(contact.name) }
        var isFavourite by rememberSaveable { mutableStateOf(contact.isFavourite) }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFavourite,
                onCheckedChange = {
                    isFavourite = it
                }
            )
            Text("Favourite")
        }
        Button(
            onClick = {
                onContactSave(contact.copy(name = name, isFavourite = isFavourite))
                name = ""
                isFavourite = false
            }
        ) {
            Text("SAVE")
        }
    }
}


