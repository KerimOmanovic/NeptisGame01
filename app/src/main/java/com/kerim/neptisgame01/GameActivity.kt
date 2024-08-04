package com.kerim.neptisgame01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kerim.neptisgame01.models.Recipe
import com.kerim.neptisgame01.network.ApiClient
import com.kerim.neptisgame01.network.RecipeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameActivity : AppCompatActivity() {
    private lateinit var recipeService: RecipeService
    private lateinit var recipeNameTextView: TextView
    private lateinit var answerField: EditText
    private lateinit var sendButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var attemptsCountTextView: TextView

    private var currentAttempt = 1
    private var userScore = 0
    private var opponentScore = 0
    private var currentRecipe: Recipe? = null
    private var recipes: List<Recipe> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        recipeService = ApiClient.createService(RecipeService::class.java)

        recipeNameTextView = findViewById(R.id.recipe_name)
        answerField = findViewById(R.id.answer_field)
        sendButton = findViewById(R.id.send_button)
        scoreTextView = findViewById(R.id.score)
        attemptsCountTextView = findViewById(R.id.attempts_count)

        loadRecipes()

        sendButton.setOnClickListener {
            val userGuess = answerField.text.toString()
            if (userGuess.isNotEmpty()) {
                checkGuess(userGuess)
                answerField.text.clear()
            }
        }
    }

    private fun loadRecipes() {
        recipeService.getAllRecipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    recipes = response.body() ?: emptyList()
                    showNextRecipe()
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                // Handle network error
            }
        })
    }

    private fun showNextRecipe() {
        if (currentAttempt > 10) {
            endGame()
            return
        }

        currentRecipe = recipes.random()
        recipeNameTextView.text = currentRecipe?.name
        attemptsCountTextView.text = "Attempt: $currentAttempt/10"
        currentAttempt++
    }

    private fun checkGuess(userGuess: String) {
        val correctTags = currentRecipe?.tags ?: emptyList()
        if (correctTags.any { it.equals(userGuess, ignoreCase = true) }) {
            userScore++
        }

        // Simulate opponent's guess
        simulateOpponentGuess()

        updateScore()
        showNextRecipe()
    }

    private fun simulateOpponentGuess() {
        val tags = recipes.flatMap { it.tags }
        val randomTag = tags.random()
        val correctTags = currentRecipe?.tags ?: emptyList()
        if (correctTags.contains(randomTag)) {
            opponentScore++
        }
    }

    private fun updateScore() {
        scoreTextView.text = "Score: $userScore vs $opponentScore"
    }

    private fun endGame() {
        val message = if (userScore > opponentScore) {
            "You won! Congrats!"
        } else {
            "You lost! More luck next time…"
        }

        // Show a popup with the result
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                // Navigate to home or another activity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }
}
