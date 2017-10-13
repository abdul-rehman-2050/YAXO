package pr02.learnkotlin.com.tictactoe

import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.testlayout.*
import java.lang.Math.random
import java.lang.Math.sqrt
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {


    private var mFirebaseAnalytics:FirebaseAnalytics?=null

    var isMyTurn = false;
    var curTurnNumber = 0;
    var isGameOver = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        switchPlayers()


        // toggleButton1= (tableLayoutBoard.width/2)



    }

    fun takeComputerMove(){
        if(!isMyTurn && !isGameOver){

            var curButton:Button? = null

            if(button5.isEnabled){
                curButton = button5

            }else if(button1.isEnabled){

                curButton = button1
            }else if(button3.isEnabled){

                curButton = button3
            }else if(button7.isEnabled){

                curButton = button7
            }else if(button9.isEnabled){
                curButton = button9
            }else if(button4.isEnabled){
                curButton = button4
            }else if(button6.isEnabled){
                curButton = button6
            }else if(button2.isEnabled){
                curButton = button2
            }else if(button8.isEnabled){
                curButton = button9
            }






            if(curButton!=null){
                curButton.isEnabled = false
                curButton.text="O"
                curButton.setTextColor(Color.RED)
            }
        }
    }

    fun performAction(view: View){

        if (isGameOver || !isMyTurn) return
        var curButton = view as Button
        //curButton.text = if(isMyTurn) "X" else "O"
        curButton.text = "X"
        curButton.isEnabled = false
        curButton.setTextColor(Color.RED)
        switchPlayers()

        takeComputerMove()
        switchPlayers()

    }

    fun switchPlayers(){

        curTurnNumber++
        val vnum = curTurnNumber-1
        val (winingPlayer, winingLineNumber) = checkWhoWins()

        if(winingPlayer=="X" || winingPlayer == "O") {

            drawWiningLine(winingLineNumber)

        }
        if(curTurnNumber>9){
            Toast.makeText(applicationContext,"Game Draw",Toast.LENGTH_SHORT).show()
            tvPlayer2.visibility = View.INVISIBLE
            tvPlayer1.visibility = View.INVISIBLE
            return
        }

        if(isMyTurn){

            isMyTurn = false
            player1Dot.visibility = View.INVISIBLE
            player2Dot.visibility = View.VISIBLE

        }else{

            isMyTurn = true
            player2Dot.visibility = View.INVISIBLE
            player1Dot.visibility = View.VISIBLE

        }




    }




    inline fun isWinRow1(): Boolean{

        if(!button1.isEnabled && !button2.isEnabled && !button3.isEnabled) {
            if (button1.text == button2.text && button2.text == button3.text) return true
        }
        return false

    }
    inline fun isWinRow2():Boolean{


        if(!button4.isEnabled && !button5.isEnabled && !button6.isEnabled){
            if(button4.text == button5.text && button5.text == button6.text)return true
        }
        return false


    }
    inline fun isWinRow3():Boolean{

        if(!button7.isEnabled && !button8.isEnabled && !button9.isEnabled) {
            if (button7.text == button8.text && button8.text == button9.text) return true
        }
        return false


    }
    inline fun isWinCol1():Boolean{
        if(!button1.isEnabled && !button4.isEnabled && !button7.isEnabled) {
            if (button1.text == button4.text && button4.text == button7.text) return true
        }
        return false

    }
    inline fun isWinCol2():Boolean{

        if(!button2.isEnabled && !button5.isEnabled && !button8.isEnabled) {
            if (button2.text == button5.text && button5.text == button8.text) return true
        }
        return false

    }
    inline fun isWinCol3():Boolean{
        if(!button3.isEnabled && !button6.isEnabled && !button9.isEnabled) {
            if (button3.text == button6.text && button6.text == button9.text) return true
        }
        return false

    }
    inline fun isWinDiag1():Boolean{

        if(!button1.isEnabled && !button5.isEnabled && !button9.isEnabled) {
            if (button1.text == button5.text && button5.text == button9.text) return true
        }
            return false

    }
    inline fun isWinDiag2():Boolean{

        if(!button3.isEnabled && !button5.isEnabled && !button7.isEnabled) {
            if (button3.text == button5.text && button5.text == button7.text) return true
        }
        return false

    }

    fun checkWhoWins(): Pair<String,Int>{

        if(isWinCol1())return Pair(button1.text.toString(),3)
        if(isWinCol2())return Pair(button2.text.toString(),4)
        if(isWinCol3())return Pair(button3.text.toString(),5)
        if(isWinRow1())return Pair(button1.text.toString(),6)
        if(isWinRow2())return Pair(button4.text.toString(),7)
        if(isWinRow3())return Pair(button7.text.toString(),8)
        if(isWinDiag2())return Pair(button3.text.toString(),2)
        if(isWinDiag1())return Pair(button1.text.toString(),1)


        return Pair("",0)
    }


    fun drawWiningLine(winNumber: Int){


        val tableBoardHeight = tableLayoutBoard.height
        val tableBoardWidth = tableLayoutBoard.width
        val tbHeightSquare = tableBoardHeight*tableBoardHeight
        val tbWidthSquare = tableBoardWidth*tableBoardWidth
        val diagnalHeight = sqrt(tbHeightSquare+tbWidthSquare.toDouble()).toInt()
        val parentWidth = relativeLayoutBoard.width
        val parentHeight = relativeLayoutBoard.height
        val halfParentHeight = parentHeight/2
        val halfParentWidth = parentWidth/2
        val relativeLayoutParamsV = RelativeLayout.LayoutParams(25,tableBoardHeight)
        val relativeLayoutParamsH = RelativeLayout.LayoutParams(tableBoardWidth,25)


        relativeLayoutParamsV.addRule(RelativeLayout.CENTER_VERTICAL)

        winingLine.rotation = 0f
        winingLine.visibility = View.VISIBLE

       // Toast.makeText(this,"width="+winNumber,Toast.LENGTH_SHORT).show()
        isGameOver=true
        when(winNumber){



            1 -> {

                relativeLayoutParamsV.marginStart = halfParentWidth
                relativeLayoutParamsV.height = diagnalHeight
                winingLine.layoutParams = relativeLayoutParamsV
                winingLine.rotation = 130f

            }
            2 ->{

                relativeLayoutParamsV.marginStart = halfParentWidth
                relativeLayoutParamsV.height = diagnalHeight
                winingLine.layoutParams = relativeLayoutParamsV
                winingLine.rotation = 45f
            }
            3 ->{

                relativeLayoutParamsV.marginStart = halfParentWidth/2
                relativeLayoutParamsV.height = tableBoardHeight
                winingLine.layoutParams = relativeLayoutParamsV


            }
            4 ->{

                relativeLayoutParamsV.marginStart = halfParentWidth-10
                relativeLayoutParamsV.height = tableBoardHeight
                winingLine.layoutParams = relativeLayoutParamsV


            }
            5 ->{

                relativeLayoutParamsV.marginStart = (halfParentWidth/2+halfParentWidth)-20
                relativeLayoutParamsV.height = tableBoardHeight
                winingLine.layoutParams = relativeLayoutParamsV


            }

            6 ->{
                relativeLayoutParamsV.addRule(RelativeLayout.CENTER_HORIZONTAL)
                relativeLayoutParamsV.removeRule(RelativeLayout.CENTER_VERTICAL)
                relativeLayoutParamsV.topMargin = (halfParentHeight/2)+40
                relativeLayoutParamsV.width = tableBoardWidth
                relativeLayoutParamsV.height = 25
                winingLine.layoutParams = relativeLayoutParamsV


            }
            7 ->{
                relativeLayoutParamsV.addRule(RelativeLayout.CENTER_HORIZONTAL)
                relativeLayoutParamsV.removeRule(RelativeLayout.CENTER_VERTICAL)
                relativeLayoutParamsV.topMargin = halfParentHeight-10
                relativeLayoutParamsV.width = tableBoardWidth
                relativeLayoutParamsV.height = 25
                winingLine.layoutParams = relativeLayoutParamsV


            }
            8 ->{
                relativeLayoutParamsV.addRule(RelativeLayout.CENTER_HORIZONTAL)
                relativeLayoutParamsV.removeRule(RelativeLayout.CENTER_VERTICAL)
                relativeLayoutParamsV.topMargin = (halfParentHeight/2+halfParentHeight)-60
                relativeLayoutParamsV.width = tableBoardWidth
                relativeLayoutParamsV.height = 25
                winingLine.layoutParams = relativeLayoutParamsV


            }






            else -> {winingLine.visibility = View.INVISIBLE;isGameOver=false}

        }



    }


}
