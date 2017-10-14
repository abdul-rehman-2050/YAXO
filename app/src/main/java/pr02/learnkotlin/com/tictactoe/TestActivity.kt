package pr02.learnkotlin.com.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    /*
    *
    * This activity is created just to make sure and check some
    * piece of code
    *
    * */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        testActivityBtn1.setOnClickListener{

            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

        }

    }
}
