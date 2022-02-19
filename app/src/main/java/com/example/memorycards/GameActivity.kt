package com.example.memorycards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.memorycards.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var imageIds = arrayListOf<Int>()

    private var counterHits = 0
    private var counterSuccess = 0
    private var firstImageId = 0
    private var secondImageId = 0

    private var firstOpenCard: ImageView? = null
    private var secondOpenCard: ImageView? = null

    private lateinit var difficulty: String
    private lateinit var catefory: String

    private val EASY: String = "easy"
    private val MEDIUM: String = "medium"
    private val HARD: String = "hard"

    private val ANIMALS: String = "animals"
    private val FOOD: String = "food"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
        setUpCards()
        setListeners()
        setTags()
    }

    private fun setUpUi() {
        difficulty = intent.getStringExtra(resources.getString(R.string.difficulty)).toString()
        catefory = intent.getStringExtra(resources.getString(R.string.category)).toString()

        when (difficulty) {
            EASY -> {
                binding.layoutRow3.visibility = View.GONE
                binding.layoutRow4.visibility = View.GONE
            }
            MEDIUM -> {
                binding.layoutRow4.visibility = View.GONE
            }
            HARD -> {
                //all cards visible
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.optionsMenuRestart -> restartGameDialog()
            R.id.optionsMenuSettings -> settingsGameDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restartGameDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Restart game")
            .setMessage("Are you sure you want to restart?")
            .setIcon(R.drawable.ic_restart)
            .setPositiveButton("YES") { _, _ -> restartGame() }
            .setNegativeButton("CANCEL") { _, _ -> }
        builder.show()
    }

    private fun settingsGameDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Go to settings")
            .setMessage("Are you sure? All unsaved progress will be lost.")
            .setIcon(R.drawable.ic_settings)
            .setPositiveButton("YES") { _, _ -> finish() }
            .setNegativeButton("CANCEL") { _, _ -> }
        builder.show()
    }

    private fun openCard(it: View?) {
        if (firstOpenCard == null) {
            counterHits++
            binding.textViewScore.text = counterHits.toString()
            firstOpenCard = it as ImageView
            firstImageId = firstOpenCard!!.tag as Int
            showCard(firstOpenCard!!, firstImageId)
            it.isEnabled = false
        } else {
            secondOpenCard = it as ImageView
            secondImageId = secondOpenCard!!.tag as Int
            showCard(secondOpenCard!!, secondImageId)
            disableAllImageViews()
            Handler(Looper.getMainLooper()).postDelayed({
                checkCards(firstImageId, secondImageId)
                checkGameOver()
            }, 500)
        }
    }

    private fun checkGameOver() {
        when (difficulty) {
            EASY -> setGameOverUi(4)
            MEDIUM -> setGameOverUi(6)
            HARD -> setGameOverUi(8)
        }
    }

    private fun setGameOverUi(successLimit: Int) {
        if (counterSuccess == successLimit) {
            disableAllImageViews()
            binding.buttonRestart.visibility = View.VISIBLE
        }
    }

    private fun enableAllImageViews() {
        binding.imageView1.isEnabled = true
        binding.imageView2.isEnabled = true
        binding.imageView3.isEnabled = true
        binding.imageView4.isEnabled = true
        binding.imageView5.isEnabled = true
        binding.imageView6.isEnabled = true
        binding.imageView7.isEnabled = true
        binding.imageView8.isEnabled = true
        binding.imageView9.isEnabled = true
        binding.imageView10.isEnabled = true
        binding.imageView11.isEnabled = true
        binding.imageView12.isEnabled = true
        binding.imageView13.isEnabled = true
        binding.imageView14.isEnabled = true
        binding.imageView15.isEnabled = true
        binding.imageView16.isEnabled = true
    }

    private fun disableAllImageViews() {
        binding.imageView1.isEnabled = false
        binding.imageView2.isEnabled = false
        binding.imageView3.isEnabled = false
        binding.imageView4.isEnabled = false
        binding.imageView5.isEnabled = false
        binding.imageView6.isEnabled = false
        binding.imageView7.isEnabled = false
        binding.imageView8.isEnabled = false
        binding.imageView9.isEnabled = false
        binding.imageView10.isEnabled = false
        binding.imageView11.isEnabled = false
        binding.imageView12.isEnabled = false
        binding.imageView13.isEnabled = false
        binding.imageView14.isEnabled = false
        binding.imageView15.isEnabled = false
        binding.imageView16.isEnabled = false
    }

    private fun checkCards(firstImageId: Int, secondImageId: Int) {
        if (firstImageId == secondImageId) {
            counterSuccess++
            firstOpenCard?.background =
                ContextCompat.getDrawable(this, R.drawable.background_card_green)
            secondOpenCard?.background =
                ContextCompat.getDrawable(this, R.drawable.background_card_green)

            Handler(Looper.getMainLooper()).postDelayed({
                firstOpenCard?.visibility = View.INVISIBLE
                secondOpenCard?.visibility = View.INVISIBLE
                firstOpenCard = null
                secondOpenCard = null
                enableAllImageViews()
            }, 500)
        } else {
            firstOpenCard?.background =
                ContextCompat.getDrawable(this, R.drawable.background_card_red)
            secondOpenCard?.background =
                ContextCompat.getDrawable(this, R.drawable.background_card_red)

            Handler(Looper.getMainLooper()).postDelayed({
                firstOpenCard?.setImageResource(R.drawable.ic_retro_question_mark)
                secondOpenCard?.setImageResource(R.drawable.ic_retro_question_mark)

                firstOpenCard?.background =
                    ContextCompat.getDrawable(this, R.drawable.background_card_orange)
                secondOpenCard?.background =
                    ContextCompat.getDrawable(this, R.drawable.background_card_orange)

                firstOpenCard = null
                secondOpenCard = null
                enableAllImageViews()
            }, 500)
        }

    }

    private fun showCard(imageView: ImageView, imageId: Int) {
        imageView.background = ContextCompat.getDrawable(this, R.drawable.background_card_yellow)
        imageView.setImageResource(imageId)
    }

    private fun setTags() {
        when (difficulty) {
            EASY -> {
                binding.imageView1.tag = imageIds[0]
                binding.imageView2.tag = imageIds[1]
                binding.imageView3.tag = imageIds[2]
                binding.imageView4.tag = imageIds[3]
                binding.imageView5.tag = imageIds[4]
                binding.imageView6.tag = imageIds[5]
                binding.imageView7.tag = imageIds[6]
                binding.imageView8.tag = imageIds[7]
            }

            MEDIUM -> {
                binding.imageView1.tag = imageIds[0]
                binding.imageView2.tag = imageIds[1]
                binding.imageView3.tag = imageIds[2]
                binding.imageView4.tag = imageIds[3]
                binding.imageView5.tag = imageIds[4]
                binding.imageView6.tag = imageIds[5]
                binding.imageView7.tag = imageIds[6]
                binding.imageView8.tag = imageIds[7]
                binding.imageView9.tag = imageIds[8]
                binding.imageView10.tag = imageIds[9]
                binding.imageView11.tag = imageIds[10]
                binding.imageView12.tag = imageIds[11]
            }

            HARD -> {
                binding.imageView1.tag = imageIds[0]
                binding.imageView2.tag = imageIds[1]
                binding.imageView3.tag = imageIds[2]
                binding.imageView4.tag = imageIds[3]
                binding.imageView5.tag = imageIds[4]
                binding.imageView6.tag = imageIds[5]
                binding.imageView7.tag = imageIds[6]
                binding.imageView8.tag = imageIds[7]
                binding.imageView9.tag = imageIds[8]
                binding.imageView10.tag = imageIds[9]
                binding.imageView11.tag = imageIds[10]
                binding.imageView12.tag = imageIds[11]
                binding.imageView13.tag = imageIds[12]
                binding.imageView14.tag = imageIds[13]
                binding.imageView15.tag = imageIds[14]
                binding.imageView16.tag = imageIds[15]
            }
        }
    }

    private fun setListeners() {
        binding.imageView1.setOnClickListener(this::openCard)
        binding.imageView2.setOnClickListener(this::openCard)
        binding.imageView3.setOnClickListener(this::openCard)
        binding.imageView4.setOnClickListener(this::openCard)
        binding.imageView5.setOnClickListener(this::openCard)
        binding.imageView6.setOnClickListener(this::openCard)
        binding.imageView7.setOnClickListener(this::openCard)
        binding.imageView8.setOnClickListener(this::openCard)
        binding.imageView9.setOnClickListener(this::openCard)
        binding.imageView10.setOnClickListener(this::openCard)
        binding.imageView11.setOnClickListener(this::openCard)
        binding.imageView12.setOnClickListener(this::openCard)
        binding.imageView13.setOnClickListener(this::openCard)
        binding.imageView14.setOnClickListener(this::openCard)
        binding.imageView15.setOnClickListener(this::openCard)
        binding.imageView16.setOnClickListener(this::openCard)
        binding.buttonRestart.setOnClickListener { restartGame() }
    }

    private fun restartGame() {
        restartScore()
        setUpCards()
        setTags()
        restartImageViews()
        setAllImageViewsVisible()
        enableAllImageViews()
        binding.buttonRestart.visibility = View.INVISIBLE
    }

    private fun setAllImageViewsVisible() {
        binding.imageView1.visibility = View.VISIBLE
        binding.imageView2.visibility = View.VISIBLE
        binding.imageView3.visibility = View.VISIBLE
        binding.imageView4.visibility = View.VISIBLE
        binding.imageView5.visibility = View.VISIBLE
        binding.imageView6.visibility = View.VISIBLE
        binding.imageView7.visibility = View.VISIBLE
        binding.imageView8.visibility = View.VISIBLE
        binding.imageView9.visibility = View.VISIBLE
        binding.imageView10.visibility = View.VISIBLE
        binding.imageView11.visibility = View.VISIBLE
        binding.imageView12.visibility = View.VISIBLE
        binding.imageView13.visibility = View.VISIBLE
        binding.imageView14.visibility = View.VISIBLE
        binding.imageView15.visibility = View.VISIBLE
        binding.imageView16.visibility = View.VISIBLE
    }

    private fun restartImageViews() {
        binding.imageView1.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView2.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView3.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView4.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView5.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView6.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView7.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView8.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView9.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView10.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView11.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView12.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView13.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView14.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView15.setImageResource(R.drawable.ic_retro_question_mark)
        binding.imageView16.setImageResource(R.drawable.ic_retro_question_mark)

        binding.imageView1.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView2.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView3.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView4.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView5.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView6.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView7.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView8.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView9.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView10.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView11.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView12.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView13.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView14.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView15.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
        binding.imageView16.background =
            ContextCompat.getDrawable(this, R.drawable.background_card_orange)
    }

    private fun restartScore() {
        counterHits = 0
        counterSuccess = 0
        binding.textViewScore.text = getString(R.string.choose_your_cards)
    }

    private fun setUpCards() {
        when (catefory) {
            FOOD -> setUpFoodCards()
            ANIMALS -> setUpAnimalCards()
        }

    }

    private fun setUpFoodCards() {
        when (difficulty) {
            EASY -> {
                imageIds.clear()
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.shuffle()
            }

            MEDIUM -> {
                imageIds.clear()
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.add(R.drawable.ic_barbeque)
                imageIds.add(R.drawable.ic_barbeque)
                imageIds.add(R.drawable.ic_burito)
                imageIds.add(R.drawable.ic_burito)
                imageIds.shuffle()
            }

            HARD -> {
                imageIds.clear()
                imageIds.clear()
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_pizza)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_cheeseburger)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_fry)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.add(R.drawable.ic_hotdog)
                imageIds.add(R.drawable.ic_barbeque)
                imageIds.add(R.drawable.ic_barbeque)
                imageIds.add(R.drawable.ic_burito)
                imageIds.add(R.drawable.ic_burito)
                imageIds.add(R.drawable.ic_donut)
                imageIds.add(R.drawable.ic_donut)
                imageIds.add(R.drawable.ic_chickens)
                imageIds.add(R.drawable.ic_chickens)
                imageIds.shuffle()
                imageIds.shuffle()
            }
        }
    }

    private fun setUpAnimalCards() {
        when (difficulty) {
            EASY -> {
                imageIds.clear()
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.shuffle()
            }

            MEDIUM -> {
                imageIds.clear()
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.add(R.drawable.ic_owl)
                imageIds.add(R.drawable.ic_owl)
                imageIds.add(R.drawable.ic_sloth)
                imageIds.add(R.drawable.ic_sloth)
                imageIds.shuffle()
            }

            HARD -> {
                imageIds.clear()
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_giraffe)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_lion)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_elephant)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.add(R.drawable.ic_monkey)
                imageIds.add(R.drawable.ic_owl)
                imageIds.add(R.drawable.ic_owl)
                imageIds.add(R.drawable.ic_sloth)
                imageIds.add(R.drawable.ic_sloth)
                imageIds.add(R.drawable.ic_rhino)
                imageIds.add(R.drawable.ic_rhino)
                imageIds.add(R.drawable.ic_penguin)
                imageIds.add(R.drawable.ic_penguin)
                imageIds.shuffle()
            }
        }
    }
}