package com.example.login_app

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.spinner_item.view.*

class myArrayAdapter (context: Context, list: List<DataSource_Spinner>): ArrayAdapter<DataSource_Spinner>(context,0, list)
{
    // Spinner Appearance when is in Close State
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)
    }

    // Spinner Appearance when is in Open State
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position,convertView,parent)

    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{

        val myelement = getItem(position)
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        view.itemImage.setImageResource(myelement!!.image)
        view.itemName.text = myelement.name

        return view
    }

}