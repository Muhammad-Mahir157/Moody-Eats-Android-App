package com.example.moodyeats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class updatePassword:AppCompatActivity() {
    lateinit var currentPassword: TextView
    lateinit var newPassword:TextView
    lateinit var renewPassword:TextView
    lateinit var user:FirebaseUser
    lateinit var mAuth: FirebaseAuth
    lateinit var resetPasswordButton: Button
    lateinit var progressbar:ProgressBar
    lateinit var currentemail:String
    var passmatch:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updatepassword)
        currentPassword=findViewById(R.id.oldPasswordin)
        newPassword=findViewById(R.id.newpasswordin)
        renewPassword=findViewById(R.id.renewpasswordin)
        mAuth= FirebaseAuth.getInstance()
        user= FirebaseAuth.getInstance().currentUser!!
        resetPasswordButton=findViewById(R.id.editPasswordButton)
        currentemail=user.email.toString()
        progressbar=findViewById(R.id.progressBarupdate)
        progressbar.visibility=View.GONE
    resetPasswordButton.setOnClickListener()
    {
        progressbar.visibility= View.VISIBLE
        updatepassword();
        finish()
    }
}
    public fun updatepassword()
    {
        val oldpasswordstring:String = currentPassword.text.toString().trim()
        val newpasswordstring:String = newPassword.text.toString().trim()
        val renewpasswordstring:String = renewPassword.text.toString().trim()
        if(!newpasswordstring.equals(renewpasswordstring))
        {   progressbar.visibility=View.GONE
            renewPassword.setError("The password does not matches new password")
            return
        }
        if(newpasswordstring.length<6)
        {   progressbar.visibility=View.GONE
            newPassword.setError("The password is too short")
            return
        }
        mAuth.signInWithEmailAndPassword(currentemail,oldpasswordstring).addOnCompleteListener{task ->
            if(!task.isSuccessful)
            {
                progressbar.visibility=View.GONE
                currentPassword.setError("Wrong Current Password entered")
                passmatch=0
            return@addOnCompleteListener
            }

                }
        if(passmatch==0)
        {return}
        user.updatePassword(newpasswordstring).addOnCompleteListener{Task->
            if(Task.isSuccessful)
            {
                progressbar.visibility=View.GONE
                Toast.makeText(this@updatePassword,"Password updated successfully",Toast.LENGTH_LONG).show()
                return@addOnCompleteListener

            }
            else{
                progressbar.visibility=View.GONE
                Toast.makeText(this@updatePassword,"Error, Try again",Toast.LENGTH_LONG).show()
                return@addOnCompleteListener
            }
        }
        return

    }

}