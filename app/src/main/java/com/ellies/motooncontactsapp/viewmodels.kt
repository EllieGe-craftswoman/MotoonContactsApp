package com.ellies.motooncontactsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactsScreenVM(private val contactDao: ContactDao): ViewModel(){
    fun getContacts() = contactDao.getAll()
}

class AddContactScreenVM(private val contactDao: ContactDao): ViewModel(){
    fun addContact(contact: Contact){
        viewModelScope.launch {
            contactDao.insert(contact)
        }
    }
}
class EditContactScreenVM(private val contactDao: ContactDao): ViewModel(){
    fun editContact(contact: Contact){
        viewModelScope.launch {
            contactDao.update(contact)
        }
    }

    fun getContact(contactId: Long): LiveData<Contact> = contactDao.get(contactId)
}

class MyViewModelFactory(private val contactDao: ContactDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactsScreenVM::class.java)){
            return ContactsScreenVM(contactDao) as T
        }
        if(modelClass.isAssignableFrom(AddContactScreenVM::class.java)){
            return AddContactScreenVM(contactDao) as T
        }
        if(modelClass.isAssignableFrom(EditContactScreenVM::class.java)){
            return EditContactScreenVM(contactDao) as T
        }
        throw IllegalArgumentException("ViewModel Couldn't be found")
    }
}