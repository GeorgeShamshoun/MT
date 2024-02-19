package com.example.mt.model.remote

import com.example.mt.model.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceAPI {

    @GET("/GeorgeShamshoun/repo/users")
     suspend  fun getAPIUsers():Response<List<User>>

    @GET("/GeorgeShamshoun/repo/users/{id}")
    suspend  fun getAPIUser(@Path("id")id : Int):Response<User>

    @GET("/GeorgeShamshoun/repo/users/")
    suspend  fun getAPIUserQuery(@Query("id")id : Int):Response<User>

    @POST("/GeorgeShamshoun/repo/users")
    suspend  fun addAPIUser(@Body user : User):Response<User>

    @PUT("/GeorgeShamshoun/repo/users/{id}")
    suspend  fun updateAPIUser(@Body user : User,@Path("id") id : Int):Response<User>

    @DELETE("/GeorgeShamshoun/repo/users/{id}")
    suspend  fun deleteAPIUser(@Path("id")id : Int)
}