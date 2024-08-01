package com.elifnuroksuz.tictacgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPlayers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_players)

        val playerOne: EditText = findViewById(R.id.playerOne)
        val playerTwo: EditText = findViewById(R.id.playerTwo)
        val startGameButton: Button = findViewById(R.id.startGameButton)

        startGameButton.setOnClickListener {
            val getPlayerOneName = playerOne.text.toString().trim()
            val getPlayerTwoName = playerTwo.text.toString().trim()

            if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                Toast.makeText(this, "Please enter both player names", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("playerOne", getPlayerOneName)
                    putExtra("playerTwo", getPlayerTwoName)
                }
                startActivity(intent)
                finish() // Aktivite kapandıktan sonra eski aktiviteyi kapatır
            }
        }
    }
}
