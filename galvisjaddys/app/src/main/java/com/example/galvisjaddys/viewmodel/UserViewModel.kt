package com.example.galvisjaddys.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galvisjaddys.model.User
import com.example.galvisjaddys.repository.UserRepository

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _selectedUser = MutableLiveData<User?>()
    val selectedUser: LiveData<User?> = _selectedUser

    init {
        loadUsers()
    }

    fun loadUsers() {
        _users.value = repository.getAllUsers()
    }

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

}
