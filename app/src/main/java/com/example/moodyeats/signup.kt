package com.example.moodyeats
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class signup:AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var email: TextView? =null
    var signupButton: Button?=null
    private var name: TextView? =null
    private var password: TextView? =null
    private var progressbar: ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        email=findViewById(R.id.emailinsignup)
        signupButton=findViewById(R.id.signupbutton)
         password=findViewById(R.id.signuppassword)

         name=findViewById(R.id.nameinsignup)
         progressbar=findViewById(R.id.progressBarsignin)
        progressbar?.visibility= View.GONE
        mAuth= FirebaseAuth.getInstance();
        signupButton?.setOnClickListener {
            signin();
        }
    }

    private fun signin() {
        val namestring:String=name?.text.toString().trim()
        val passwordstring:String= password?.text.toString().trim()
        val emailstring:String = email?.text.toString().trim()
        if (namestring.isEmpty()){
            name?.setError("Name is required")
            name?.requestFocus()
            return
        }
        if (emailstring.isEmpty() || emailstring==null){
            email?.setError("Email is required")
            email?.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailstring).matches())
        {
            email?.setError("Please enter a valid email")
            email?.requestFocus()
            return
        }
        if (passwordstring.isEmpty()){
            password?.setError("Password cannot be empty")
            password?.requestFocus()
            return
        }
        if (passwordstring.length<6){
            password?.setError("Password must be at least 6 characters long")
            password?.requestFocus()
            return
        }
        progressbar?.visibility=View.VISIBLE
        mAuth?.createUserWithEmailAndPassword(emailstring,passwordstring)
            ?.addOnCompleteListener{task ->
                if(task.isSuccessful)
                {var user=User(namestring,emailstring)
                    FirebaseAuth.getInstance().currentUser?.let {
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(it.uid)
                            .setValue(user).addOnCompleteListener { task ->

                                if(task.isSuccessful) {
                                    Toast.makeText(this, "User has been registered successfuly",Toast.LENGTH_LONG).show()
                                    progressbar?.visibility=View.GONE
                                    intent= Intent(this@signup,home::class.java)
                                    startActivity(intent)
                                } else{
                                    Toast.makeText(this, "Error registering user try again",Toast.LENGTH_LONG).show()
                                    progressbar?.visibility=View.GONE
                                }
                            }
                    }



                }
                else{
                    Toast.makeText(this, "Error registering user try again",Toast.LENGTH_LONG).show()
                    progressbar?.visibility=View.GONE
                }
            }


    }
}

