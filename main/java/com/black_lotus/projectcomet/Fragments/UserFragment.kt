package com.black_lotus.projectcomet.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.black_lotus.projectcomet.Adapter.AdapterUser
import com.black_lotus.projectcomet.Models.Users
import com.black_lotus.projectcomet.R
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserFragment : Fragment() {

    private var userAdapter : AdapterUser?=null
    private var userList : List<Users>?=null
    private var rvUsers : RecyclerView?=null
    private lateinit var db_search_User : EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user, container, false)

        rvUsers = view.findViewById(R.id.RV_Users)
        rvUsers!!.setHasFixedSize(true)
        rvUsers!!.layoutManager = LinearLayoutManager(context)
        db_search_User = view.findViewById(R.id.DB_search_user)

        userList = ArrayList()
        obtainUserBD()

        db_search_User.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s_user: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchUsername(s_user.toString().lowercase())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        return view
    }

    private fun obtainUserBD() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val reference = FirebaseDatabase.getInstance().reference.child("Usernames").orderByChild("n_username")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (userList as ArrayList<Users>).clear()
                if(db_search_User.text.toString().isEmpty()){
                    for (sh in snapshot.children){
                        val user : Users?= sh.getValue(Users::class.java)
                        if (!(user!!.getUid()).equals(firebaseUser)){
                            (userList as ArrayList<Users>).add(user)
                        }
                    }
                    userAdapter = AdapterUser(context!!, userList!!)
                    rvUsers!!.adapter = userAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun searchUsername(searchUser : String){
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val consult = FirebaseDatabase.getInstance().reference.child("Usernames").orderByChild("search")
            .startAt(searchUser).endAt(searchUser + "\uf8ff")
        consult.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (userList as ArrayList<Users>).clear()
                for (sh in snapshot.children){
                    val user : Users?= sh.getValue(Users::class.java)
                    if (!(user!!.getUid()).equals(firebaseUser)){
                        (userList as ArrayList<Users>).add(user)
                    }
                }
                userAdapter = AdapterUser(context!!, userList!!)
                rvUsers!!.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}