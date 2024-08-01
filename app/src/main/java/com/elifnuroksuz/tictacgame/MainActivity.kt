package com.elifnuroksuz.tictacgame

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.elifnuroksuz.tictacgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val combinationList = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(2, 4, 6),
        intArrayOf(0, 4, 8)
    )
    private var boxPositions = IntArray(9) // 9 sıfır
    private var playerTurn = 1
    private var totalSelectedBoxes = 0 // Başlangıçta 0 olarak ayarladım

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oyuncu isimlerini al ve göster
        val playerOneName = intent.getStringExtra("playerOne") ?: "Player 1"
        val playerTwoName = intent.getStringExtra("playerTwo") ?: "Player 2"
        binding.playerOneName.text = playerOneName
        binding.playerTwoName.text = playerTwoName

        // Oyun kutularının tıklanabilirliğini ayarla
        val imageViews = listOf(
            binding.image1, binding.image2, binding.image3,
            binding.image4, binding.image5, binding.image6,
            binding.image7, binding.image8, binding.image9
        )
        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener { view ->
                if (isBoxSelectable(index)) performAction(view as ImageView, index)
            }
        }
    }

    private fun performAction(imageView: ImageView, selectedBoxPosition: Int) {
        boxPositions[selectedBoxPosition] = playerTurn

        // Oyun işlemleri ve sonuç kontrolü
        if (checkResults()) {
            val winnerName = if (playerTurn == 1) binding.playerOneName.text else binding.playerTwoName.text
            ResultDialog(this, "$winnerName is a Winner!", this).apply {
                var isCancelable = false
                show()
            }
        } else if (totalSelectedBoxes == 8) { // 9. kutu seçilmişse maç berabere
            ResultDialog(this, "Match Draw", this).apply {
                var isCancelable = false
                show()
            }
        } else {
            totalSelectedBoxes++
            changePlayerTurn(if (playerTurn == 1) 2 else 1)
        }

        imageView.setImageResource(if (playerTurn == 1) R.drawable.ximage else R.drawable.oimage)
    }

    private fun changePlayerTurn(nextPlayerTurn: Int) {
        playerTurn = nextPlayerTurn
        val playerOneBorder = if (playerTurn == 1) R.drawable.black_border else R.drawable.white_box
        val playerTwoBorder = if (playerTurn == 2) R.drawable.black_border else R.drawable.white_box

        binding.playerOneLayout.setBackgroundResource(playerOneBorder)
        binding.playerTwoLayout.setBackgroundResource(playerTwoBorder)
    }

    private fun checkResults(): Boolean {
        return combinationList.any { combination ->
            val (a, b, c) = combination
            boxPositions[a] == playerTurn && boxPositions[b] == playerTurn && boxPositions[c] == playerTurn
        }
    }

    private fun isBoxSelectable(boxPosition: Int): Boolean {
        return boxPositions[boxPosition] == 0
    }

    fun restartMatch() {
        boxPositions.fill(0) // 9 sıfırla doldur
        playerTurn = 1
        totalSelectedBoxes = 0

        val whiteBoxResId = R.drawable.white_box
        listOf(
            binding.image1, binding.image2, binding.image3,
            binding.image4, binding.image5, binding.image6,
            binding.image7, binding.image8, binding.image9
        ).forEach { it.setImageResource(whiteBoxResId) }

        changePlayerTurn(1) // Başlangıçta oyuncu 1'e döndür
    }
}
