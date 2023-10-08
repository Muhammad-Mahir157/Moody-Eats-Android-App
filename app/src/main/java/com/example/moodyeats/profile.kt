package com.example.moodyeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class profile : AppCompatActivity() {
    lateinit var email:TextView
    lateinit var nameT:TextView
    lateinit var userid:String
    lateinit var name:String
    lateinit var Email:String
    lateinit var reference: DatabaseReference
    lateinit var changepassword: Button
    lateinit var logoutbutton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        nameT=findViewById(R.id.name_textview)
        email=findViewById(R.id.email_textview)
        logoutbutton=findViewById(R.id.logoutbutton)
        changepassword=findViewById(R.id.chngpsswrdbutton)
        changepassword.isEnabled=false
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
                    Email = UserProfile.getemail()
                    if (!name.isEmpty() && !name.isNullOrEmpty() && !Email.isEmpty() && !Email.isNullOrEmpty()) {

                        nameT.setText(name)
                        nameT.visibility= View.VISIBLE
                        email.setText(Email)
                        email.visibility= View.VISIBLE
                        //redirect to user profile
                        changepassword.isEnabled=true
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(this@profile,"Something worng happened", Toast.LENGTH_LONG)

            }
        })
    logoutbutton.setOnClickListener {
        logout()
    }
        changepassword.setOnClickListener {
            intent= Intent(this,updatePassword::class.java)
            startActivity(intent) }
    }
    public fun logout()
    {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}