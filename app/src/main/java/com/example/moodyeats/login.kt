package com.example.moodyeats

import android.content.Intent
import android.media.MediaCas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.util.*

class login : AppCompatActivity() {
    lateinit var signupButton: TextView
    lateinit var loginButton:Button
    lateinit var email:TextView
    lateinit var password:TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var progressbar:ProgressBar
    lateinit var reference:DatabaseReference
    lateinit var userid:String
    lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginButton=findViewById(R.id.login)
        signupButton=findViewById(R.id.loginRegister)
        progressbar=findViewById(R.id.progressbar)
        email=findViewById(R.id.emailin)
        mAuth= FirebaseAuth.getInstance();
        password =findViewById(R.id.loginpassword)
        progressbar.visibility=View.GONE
        signupButton.setOnClickListener{
            intent= Intent(this,signup::class.java)
            startActivity(intent)

        }
        loginButton.setOnClickListener{
            userLogin()
        }}
    private fun userLogin()
    {
        val emailstring:String = email.text.toString().trim()
        val passwordstring:String=password.text.toString().trim()
        if (emailstring.isEmpty()){
            email.setError("Email is required")
            email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailstring).matches())
        {
            email.setError("Please enter a valid email")
            email.requestFocus()
            return
        }
        if (passwordstring.isEmpty()){
            password.setError("Password is required")
            password.requestFocus()
            return
        }
        if (passwordstring.length<6){
            password.setError("Password must be at least 6 characters long")
            password.requestFocus()
            return
        }
        progressbar.visibility= View.VISIBLE
        mAuth.signInWithEmailAndPassword(emailstring,passwordstring).addOnCompleteListener{task ->
            if(task.isSuccessful)
            {var user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
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

                                Toast.makeText(this@login, "Hi $name", Toast.LENGTH_LONG).show()
                                progressbar.visibility=View.GONE
                                //redirect to user profile
                                intent= Intent(this@login,home::class.java)
                                startActivity(intent)

                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Toast.makeText(this@login,"Something worng happened",Toast.LENGTH_LONG).show()
                        progressbar.visibility=View.GONE
                    }
                })

            }
            else
            {
                Toast.makeText(this, "Error signing in user, Please check your credentials", Toast.LENGTH_LONG).show()
                progressbar.visibility=View.GONE
            }

        }

    }
}




