package com.black_lotus.projectcomet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.replace
import androidx.viewpager.widget.ViewPager
import com.black_lotus.projectcomet.Fragments.ChatsFragment
import com.black_lotus.projectcomet.Fragments.UserFragment
import com.black_lotus.projectcomet.Models.Users
import com.black_lotus.projectcomet.Profile.ProfileActivity
import com.black_lotus.projectcomet.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    var reference : DatabaseReference?=null
    var firebaseUser : FirebaseUser?=null
    private lateinit var user_name: TextView

    /** This on Create is a temporary log out button **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //enableEdgeToEdge()
        initializeComponents()
        obtainData()

    } /** End of log out button **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nProfile->{
                val intent = Intent(applicationContext, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.nAbout->{
                Toast.makeText(applicationContext, "Please donate, I am poor", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.nSignout->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, StartActivity::class.java)
                Toast.makeText(applicationContext, "Signed out succesfully", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initializeComponents(){

        val toolbar : Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().reference.child("Usernames").child(firebaseUser!!.uid)
        user_name = findViewById(R.id.user_Name_Text)

        val tabLayout: TabLayout = findViewById(R.id.TabLayoutMain)
        val viewPager: ViewPager =  findViewById(R.id.ViewPagerMain)

        val viewpagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewpagerAdapter.addItem(UserFragment(), "Users")
        viewpagerAdapter.addItem(ChatsFragment(), "Chats")

        viewPager.adapter = viewpagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun obtainData(){
        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val user : Users? = snapshot.getValue(Users::class.java)
                    user_name.text = user!!.getN_Username()
                    Toast.makeText(applicationContext, "Succeeded", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "IT FAILED AGAIN", Toast.LENGTH_SHORT).show()
            }

        })
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()


        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

        fun addItem(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

    }



}