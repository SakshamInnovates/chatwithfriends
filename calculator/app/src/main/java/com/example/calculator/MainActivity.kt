package com.example.calculator

import android.os.Bundle
import android.view.View
import android.view.ViewDebug.IntToString
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.corpackage com.example.simplecalculator


import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var input: EditText
    private var operator: String = ""
    private var firstOperand: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById(R.id.input)

        // Initialize buttons
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
        val buttonClear = findViewById<Button>(R.id.buttonClear)

        // Set button click listeners
        button1.setOnClickListener { appendNumber("1") }
        button2.setOnClickListener { appendNumber("2") }
        button3.setOnClickListener { appendNumber("3") }
        button4.setOnClickListener { appendNumber("4") }
        buttonAdd.setOnClickListener { setOperator("+") }
        buttonSubtract.setOnClickListener { setOperator("-") }
        buttonMultiply.setOnClickListener { setOperator("*") }
        buttonDivide.setOnClickListener { setOperator("/") }
        buttonEquals.setOnClickListener { calculateResult() }
        buttonClear.setOnClickListener { clearInput() }
    }

    private fun appendNumber(number: String) {
        input.append(number)
    }

    private fun setOperator(op: String) {
        operator = op
        firstOperand = input.text.toString().toDoubleOrNull() ?: 0.0
        input.text.clear()
    }

    private fun calculateResult() {
        val secondOperand = input.text.toString().toDoubleOrNull()
        if (secondOperand == null) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "*" -> firstOperand * secondOperand
            "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                Toast.makeText(this, "Unknown operator", Toast.LENGTH_SHORT).show()
                return
            }
        }

        input.setText(result.toString())
        operator = ""
    }

    private fun clearInput() {
        input.text.clear()
        operator = ""
        firstOperand = 0.0
    }
}
