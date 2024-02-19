package com.example.mt.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mt.R
import com.example.mt.adapter.OnListItemClick
import com.example.mt.adapter.UserRecyclerView
import com.example.mt.databinding.ActivityUsersBinding
import com.example.mt.model.entity.User
import com.example.mt.ui.usersList.UsersViewModel


class UsersActivity : AppCompatActivity(), OnListItemClick {

    lateinit var binding: ActivityUsersBinding

    val userRecyclerView : UserRecyclerView by lazy { UserRecyclerView() }
    lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.rvShowData.adapter = userRecyclerView

        getAllUser ()

        userRecyclerView.onListItemClick = this
        viewModel.userLiveData.observe(this,
            Observer {
                if (it != null){
                userRecyclerView.setList(it)
                    binding.progressBar.visibility=View.GONE}
            })

        binding.btnAdd.setOnClickListener {
            val namee = binding.edtName.text.toString()
            val mess = binding.edtMessage.text.toString()
            viewModel.addUser(User(0,namee,mess,R.drawable.user))

           binding.edtName.setText("")
           binding.edtMessage.setText("")
           getAllUser ()
        }

    }
    fun getAllUser (){
        viewModel.getUsers()
        binding.progressBar.visibility=View.VISIBLE
        }
    override fun onItemClick(user: User) {
        viewModel.deleteUser(user )
        Toast.makeText(this,"User ${user.name} Delete Successfully",Toast.LENGTH_LONG).show()
         getAllUser ()
    }
    }

