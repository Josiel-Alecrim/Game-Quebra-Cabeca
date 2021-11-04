package com.josielsantos.numberpulzzgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private  var mainView:ViewGroup?=null
    private  var board:Board?=null
    private  var boardView:BoardView?=null
    private lateinit var moves: TextView
    private var boardSize = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**set findId*/
        mainView = findViewById(R.id.mainView)
        moves = findViewById(R.id.moves)
        moves.setTextColor(Color.RED)
        moves.textSize = 22f
        newGame()

    }

    private fun newGame() {
        board = Board(boardSize)
        board!!.addBoardChangeListener(boardChangeListener)
        board!!.rearrange()
        mainView!!.removeView(boardView)
        boardView = BoardView(this,board!!)
        mainView!!.addView(boardView)
        moves.text = "Número de movimentos : 0"
    }

    fun changeSize(newSize: Int) {
        if (newSize != boardSize) {
            boardSize = newSize
            newGame()
            boardView!!.invalidate()
        }
    }

    private val boardChangeListener :BoardChangeListener = object :BoardChangeListener{
        override fun tileSlid(from: Place?, to: Place?, numOfMoves: Int) {
            moves.text = "Número de movimentos : ${numOfMoves}"
        }

        override fun solved(numOfMoves: Int) {

            moves.text = "Resolvido em ${numOfMoves} movimentos"

            AlertDialog.Builder(this@MainActivity)
                .setTitle("You won .. !!")
                .setIcon(R.drawable.ic_celebration)
                .setMessage("Você finalizou o quebra-cabeça em $numOfMoves movimentos !! \nDeseja Jogar novamente ? ")
                .setPositiveButton("Yes"){
                        dialog,_->
                    board!!.rearrange()
                    moves.text = "Número de movimentos : 0"
                    boardView!!.invalidate()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                        dialog, _->
                    dialog.dismiss()
                }
                .create()
                .show()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {

                val settings = SettingsDialogFragment(boardSize)
                settings.show(supportFragmentManager, "configurações de fragmento")
            }
            R.id.action_new_game ->
            {
                android.app.AlertDialog.Builder(this)
                    .setTitle("Novo jogo")
                    .setMessage("\n" + "Tem certeza de que deseja começar um novo jogo??")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { dialog, which ->
                        board!!.rearrange()
                        moves.text = "Número de movimentos: 0"
                        boardView!!.invalidate()
                    }
                    .setNegativeButton(
                        android.R.string.no
                    ) { dialog, which ->
                        // do nothing
                    }.setIcon(R.drawable.ic_new_game)
                    .show()
            }
            R.id.action_help -> {
                android.app.AlertDialog.Builder(this)
                    .setTitle("Instruções")
                    .setMessage(
                        "O objetivo do quebra-cabeça é colocar as peças em ordem, fazendo movimentos deslizantes que usam o espaço vazio. Os únicos movimentos válidos devem mover um ladrilho imediatamente adjacente ao espaço em branco na localização do espaço em branco."
                    )
                    .setPositiveButton(
                        "Entendido. Vamos jogar!"
                    ) { dialog, which -> dialog.dismiss() }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}