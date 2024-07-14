package com.black_lotus.projectcomet.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.black_lotus.projectcomet.Models.Users
import com.black_lotus.projectcomet.R
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class AdapterUser (context : Context, userList : List<Users>) : RecyclerView.Adapter<AdapterUser.ViewHolder?>(){

    private val context: Context
    private val userList : List<Users>

    init {
        this.context = context
        this.userList = userList
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name_user : TextView
        var email_user : TextView
        var image_user : ImageView

        init {
            name_user = itemView.findViewById(R.id.item_Name_User)
            email_user = itemView.findViewById(R.id.item_Email_User)
            image_user = itemView.findViewById(R.id.item_Image_User)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : Users = userList[position]
        holder.name_user.text = user.getN_Username()
        holder.email_user.text = user.getEmail()
        Glide.with(context).load(user.getImage()).placeholder(R.drawable.ic_item_user).into(holder.image_user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}