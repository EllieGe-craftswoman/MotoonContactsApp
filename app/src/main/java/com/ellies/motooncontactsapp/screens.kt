package com.ellies.motooncontactsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactsScreen(
    vmf: MyViewModelFactory,
    onEditTaskClicked: (Long) -> Unit
) {
    val contactsVM: ContactsScreenVM = viewModel(factory = vmf)

    val contacts by contactsVM.getContacts().observeAsState()

    Column {
        contacts?.let { ContactsList(it, onEditTaskClicked ) }
    }
}

@Composable
fun AddContactScreen(
    vmf: MyViewModelFactory,
    onContactSaved: () -> Unit
) {
    val addContactVM: AddContactScreenVM = viewModel(factory = vmf)

    Column {
        ContactEditor(
            contact = Contact(name = "", isFavourite = false),
            onContactSave = {
                addContactVM.addContact(it)
                onContactSaved()
            }
        )
    }
}

@Composable
fun EditContactScreen(
    vmf: MyViewModelFactory,
    contactId: Long,
    onContactSaved: () -> Unit
) {
    val editContactVM: EditContactScreenVM = viewModel(factory = vmf)

    val contact = editContactVM.getContact(contactId).observeAsState()


        Column {
            contact.value?.let {
            ContactEditor(
                contact = it,
                onContactSave = {
                    editContactVM.editContact(it)
                    onContactSaved()
                }
            )
        }
    }


}