package com.example.moodyeats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class home:AppCompatActivity() {
    lateinit var reference:DatabaseReference
    lateinit var userid:String
    lateinit var showname: TextView
    lateinit var startButton: Button
    lateinit var name:String
    lateinit var profileview:ImageView
    lateinit var progressbar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        showname=findViewById(R.id.namewelcome)
        startButton=findViewById(R.id.startButton)
        profileview=findViewById(R.id.imageViewprofile)
        progressbar=findViewById(R.id.progressBarwelcome)
        progressbar.visibility=View.VISIBLE
        showname.visibility=View.GONE
        startButton.isEnabled=false
        var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        reference= FirebaseDatabase.getInstance().getReference("Users")
        if (user != null) {
            userid=user.uid
        }
        reference.child(userid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var UserProfile:User
                UserProfile= dataSnapshot.getValue(User::class.java)!!
                if(UserProfile!=null){
                    name = UserProfile.getname()
                    if (!name.isEmpty() && !name.isNullOrEmpty()) {

                        showname.setText("Hi $name\nWelcome to Moody Eats")
                        progressbar.visibility=View.GONE
                        showname.visibility=View.VISIBLE
                        //redirect to user profile
                        startButton.isEnabled=true
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(this@home,"Something worng happened", Toast.LENGTH_LONG)

            }
        })
    startButton.setOnClickListener {
        intent= Intent(this,quizmain::class.java)
        startActivity(intent)
    }
        profileview.setOnClickListener {
            intent= Intent(this,profile::class.java)
            startActivity(intent)
        }
    }

}
