package com.example.mt.ui.usersList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.mt.model.entity.User
import com.example.mt.model.local.LocalRepositoryImp
import com.example.mt.model.local.UserDatabase
import com.example.mt.model.remote.RemoteRepositoryImp
import com.example.mt.model.remote.RetroBulider
import com.example.mt.model.remote.ServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel (app: Application): AndroidViewModel(app){

     private var localRepositoryImp : LocalRepositoryImp
     private var remoteRepositoryImp : RemoteRepositoryImp

    init {
        val db = UserDatabase.getInstance(app)
        localRepositoryImp = LocalRepositoryImp(db)

        val serviceInstance = RetroBulider.getRetroBuilder().create(ServiceAPI::class.java)
        remoteRepositoryImp  = RemoteRepositoryImp(serviceInstance)
    }

    private var usersMultableLiveData = MediatorLiveData<List<User>>()
    val userLiveData :LiveData<List<User>> get() = usersMultableLiveData

    private var usersAPIMultableLiveData = MediatorLiveData<List<User>>()
    val userAPILiveData :LiveData<List<User>> get() = usersAPIMultableLiveData

    private var addUsersAPIMultableLiveData = MediatorLiveData<User>()
    val addUserAPILiveData :LiveData<User> get() = addUsersAPIMultableLiveData

    fun getUsers()= viewModelScope.launch{
        usersMultableLiveData.postValue(localRepositoryImp.getUser())
    }

    fun getUsersAPI()= viewModelScope.launch{
       var result = remoteRepositoryImp.getAPIUsers()
        if (result.isSuccessful){
            if(result.body() != null ){ usersAPIMultableLiveData.postValue(result.body()) }
        }else{ Log.i("Erroreee",result.message()) }
    }

    fun addUserAPI(user: User)= viewModelScope.launch{
        var result = remoteRepositoryImp.addAPIUser(user)
        if (result.isSuccessful){
            if(result.body() != null ){ addUsersAPIMultableLiveData.postValue(result.body()) }
        }else{ Log.i("Erroreee",result.message()) }
    }

    fun deleteAPIUser ( id : Int){
        viewModelScope.launch {
            remoteRepositoryImp.deleteAPIUser(id)
        }
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            localRepositoryImp.insertOrUpdateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            localRepositoryImp.deleteUser(user)
        }
    }
}