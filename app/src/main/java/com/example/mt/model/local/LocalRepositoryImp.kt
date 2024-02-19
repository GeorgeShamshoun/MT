package com.example.mt.model.local

import com.example.mt.model.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryImp(private val db:UserDatabase) : LocalRepository{
    override suspend fun getUser()= withContext(Dispatchers.IO){
            db.userDao().getUser()}


    override suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO){
     db.userDao().deleteUser(user)}
    }

    override suspend fun insertOrUpdateUser(user: User) {
        withContext(Dispatchers.IO){
        db.userDao().insertOrUpdateUser(user)}
    }

}