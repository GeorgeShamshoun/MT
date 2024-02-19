package com.example.mt.model.local

import com.example.mt.model.entity.User

interface LocalRepository {

    suspend  fun getUser():List<User>
    suspend  fun deleteUser (user: User)
    suspend  fun insertOrUpdateUser (user: User)

}