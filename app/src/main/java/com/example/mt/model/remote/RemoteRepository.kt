package com.example.mt.model.remote

import com.example.mt.model.entity.User
import retrofit2.Response

interface RemoteRepository {
    suspend  fun getAPIUsers(): Response<List<User>>

    suspend  fun getAPIUser(id : Int):Response<User>

    suspend  fun getAPIUserQuery(id : Int):Response<User>

    suspend  fun addAPIUser( user : User):Response<User>

    suspend  fun updateAPIUser( user : User,  id : Int):Response<User>

    suspend  fun deleteAPIUser(id : Int)
}