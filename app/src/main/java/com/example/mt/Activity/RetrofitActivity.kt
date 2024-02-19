package com.example.mt.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mt.adapter.OnListItemClick
import com.example.mt.adapter.UserRecyclerView
import com.example.mt.databinding.ActivityRetrofitBinding
import com.example.mt.model.entity.User
import com.example.mt.ui.usersList.UsersViewModel


class RetrofitActivity : AppCompatActivity(), OnListItemClick {
    lateinit var binding: ActivityRetrofitBinding
    val userRecyclerView: UserRecyclerView by lazy { UserRecyclerView() }
    lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.rvShowData.adapter = userRecyclerView


        userRecyclerView.onListItemClick = this
        viewModel.userAPILiveData.observe(this,
            Observer {
                if (it != null) {
                    userRecyclerView.setList(it)
                    binding.progressBar.visibility = View.GONE
                }
            })

        getAllUser()
        binding.btnWebR.setOnClickListener {

            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://my-json-server.typicode.com/GeorgeShamshoun/repo/users")
            startActivity(i)
        }

    }

    fun getAllUser() {

        viewModel.getUsersAPI()
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(user: User) {
        Toast.makeText(this, "User : ${user.name} \n Say :  ${user.message}", Toast.LENGTH_LONG)
            .show()
    }
}

