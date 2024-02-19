package com.example.mt

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.list_row.view.imageNum
import kotlinx.android.synthetic.main.list_row.view.txtDes
import kotlinx.android.synthetic.main.list_row.view.txtName

class NumberAdapter (context : Context , numbesr: ArrayList<NumberC>): ArrayAdapter<NumberC> (context , 0,numbesr){


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView= LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)

        var name : String? = null
        var des : String? = null
        var image : Int? = null

       var number =  getItem(position)
        listItemView.txtName.text =  number?.name
        listItemView.txtDes.text = number?.des
        listItemView.imageNum.setImageResource(number?.image!!)
        return listItemView
    }
}


