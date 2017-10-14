package pr02.learnkotlin.com.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {




    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()


    }


    override fun onStart() {

        if(mAuth !=null){

            val user = FirebaseAuth.getInstance().currentUser
            if(user!=null){

                startMainActivity()

            }
        }

        super.onStart()
    }


    fun startMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("USER_NAME", "xyz@gmail.com")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun tryMeLogIn(view: View){


        btnRegister.visibility = View.GONE

        if(mAuth!=null){
            val mEmail = edtEmail.text.toString()
            val mPass = edtPassword.text.toString()

            if(mEmail.contains("@gmail.com") && mPass.length>5){
                mAuth!!.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(this){task ->

                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Login Successful",Toast.LENGTH_LONG).show()
                        val user = FirebaseAuth.getInstance().currentUser
                        if(user!=null){

                            startMainActivity()

                        }

                    }else {

                        Toast.makeText(applicationContext,"Error while Login ${task.exception}",Toast.LENGTH_LONG).show()
                    }

                }

            }

        }
    }

    fun registerNewUser(view: View){

        btnLogin.visibility = View.GONE

        val mEmail = edtEmail.text.toString()
        val mPass = edtPassword.text.toString()

        if(mPass.length<6 || !mEmail.contains("@gmail.com"))return


        if(mAuth!=null){

            mAuth!!.createUserWithEmailAndPassword(edtEmail.text.toString(),edtPassword.text.toString()).addOnCompleteListener(this){ task ->


                if(task.isSuccessful){
                    Toast.makeText(applicationContext,"User Created Successfully",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(applicationContext,"Error in Registering ${task.exception}",Toast.LENGTH_LONG).show()
                    btnLogin.visibility = View.VISIBLE
                }

            }

        }


    }


}
