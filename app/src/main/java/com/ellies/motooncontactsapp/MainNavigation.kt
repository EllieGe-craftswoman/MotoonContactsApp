package com.ellies.motooncontactsapp

import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.MutatePriority
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainNavigation(vmf: MyViewModelFactory) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "contacts" //initial route
    ) {
        composable("contacts") {
            MyScaffold(
                title = "Contacts",
                fabIcon = Icons.Default.Add,
                onFabClicked = { navController.navigate("contacts/add") },
                content = {
                    ContactsScreen(
                        vmf = vmf,
                        onEditTaskClicked = { navController.navigate("contacts/edit/${it}") }
                    )
                }
            )
        }

        composable("contacts/add") {
            MyScaffold(title = "Add Contact", content = {
                AddContactScreen(vmf = vmf) { navController.navigate("contacts") }
            })
        }

        composable(
            route = "contacts/edit/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) {
            it.arguments?.let { bundle ->
                val id = bundle.getLong("id")
                MyScaffold(title = "Edit Contact", content = {
                    EditContactScreen(
                        vmf = vmf,
                        contactId = id
                    ) { navController.navigate("contacts") }
                })

            }
        }
    }
}