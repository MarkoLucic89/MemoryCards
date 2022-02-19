package com.example.memorycards

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.memorycards.databinding.ActivityDifficultyBinding

class DifficultyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDifficultyBinding
    private var difficulty: String? = null
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_difficulty, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionsMenuExit -> exitGameDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitGameDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Exit game")
            .setMessage("Do you really want to leave the game?")
            .setIcon(R.drawable.ic_exit)
            .setPositiveButton("YES") { _, _ -> finish() }
            .setNegativeButton("CANCEL") { _, _ -> }
        builder.show()
    }


    private fun setListeners() {
        binding.buttonEasy.setOnClickListener {
            setDifficulty(resources.getString(R.string.easy), it)
        }
        binding.buttonMedium.setOnClickListener {
            setDifficulty(resources.getString(R.string.medium), it)
        }
        binding.buttonHard.setOnClickListener {
            setDifficulty(resources.getString(R.string.hard), it)
        }
        binding.buttonAnimals.setOnClickListener {
            setCategory(resources.getString(R.string.animals), it)
        }
        binding.buttonFood.setOnClickListener {
            setCategory(resources.getString(R.string.food), it)
        }
        binding.buttonStartGame.setOnClickListener { startGame() }
    }

    private fun startGame() {
        when {
            difficulty == null -> {
                Toast.makeText(this, "PLEASE, PICK SOME DIFFICULTY", Toast.LENGTH_SHORT).show()
            }
            category == null -> {
                Toast.makeText(this, "PLEASE, PICK SOME CATEGORY", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(resources.getString(R.string.difficulty), difficulty)
                intent.putExtra(resources.getString(R.string.category), category)
                startActivity(intent)
            }
        }
    }

    private fun setCategory(string: String, view: View) {
        resetCategoryButtonColors()
        view.background = ContextCompat.getDrawable(this, R.drawable.background_card_green)
        category = string
    }

    private fun setDifficulty(string: String, view: View) {
        resetDifficultyButtonColors()
        view.background = ContextCompat.getDrawable(this, R.drawable.background_card_green)
        difficulty = string
    }

    private fun resetDifficultyButtonColors() {
        binding.buttonEasy.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.buttonMedium.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.buttonHard.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
    }

    private fun resetCategoryButtonColors() {
        binding.buttonAnimals.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.buttonFood.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
    }

    override fun onRestart() {
        super.onRestart()
        category = null
        difficulty = null
        resetCategoryButtonColors()
        resetDifficultyButtonColors()
    }

    override fun onBackPressed() {
        exitGameDialog()
    }
}
