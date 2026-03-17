package com.example.galvisjaddys.repository

import com.example.galvisjaddys.model.User

class UserRepository {

    private val users = mutableListOf(
        User(1, "Juan Garcia", "juan@email.com", 25),
        User(2, "Maria Lopez", "maria@email.com", 30),
        User(3, "Carlos Rodriguez", "carlos@email.com", 28),
        User(4, "Ana Martinez", "ana@email.com", 22),
        User(5, "Pedro Sanchez", "pedro@email.com", 35)
    )

    fun getAllUsers(): List<User> {
        return users.toList()
    }

    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun deleteUser(userId: Int) {
        users.removeAll { it.id == userId }
    }

}
