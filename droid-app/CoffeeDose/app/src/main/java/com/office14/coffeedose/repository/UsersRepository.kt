package com.office14.coffeedose.repository

import com.office14.coffeedose.database.UserDao
import com.office14.coffeedose.database.UserDbo
import com.office14.coffeedose.domain.User
import javax.inject.Inject

class UsersRepository @Inject constructor(private val usersDao : UserDao) {

    fun getUserByEmail(email:String) : User? {
        val users = usersDao.getByEmail(email).value
        if (users?.size == 1) return users[0].toDomainModel()
        return null
    }

    fun updateUser(user:User) {
        usersDao.insertAllUsers(UserDbo(user))
    }
}