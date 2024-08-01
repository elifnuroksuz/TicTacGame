package com.elifnuroksuz.tictacgame

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultDialog(
    context: Context,
    private val message: String,
    private val mainActivity: MainActivity
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_diolog)  // Layout dosyasının doğru adı

        val messageText: TextView = findViewById(R.id.messageText)
        val startAgainButton: Button = findViewById(R.id.startAgainButton)

        messageText.text = message

        startAgainButton.setOnClickListener {
            mainActivity.restartMatch()
            dismiss()
        }
    }

    override fun show() {
        super.show()
        setCancelable(false) // Set the dialog as non-cancelable
    }
}
