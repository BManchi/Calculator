package com.bmanchi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

private const val OPERAND_CONTENTS = "Operand contents"
private const val OPERATION_CONTENTS = "Operation contents"
private const val OPERAND_BOOLEAN = "Operation is null"

class MainActivity : AppCompatActivity() {

    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //variables to hold the operands and type of calculation
    private var operand1:Double? = null
    // private var operand2:Double = 0.0 //moved for narrow scope
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        // Data definitions for buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.dot)

        // Operation buttons

        val buttonEqual: Button = findViewById(R.id.equal)
        val buttonMinus: Button = findViewById(R.id.minus)
        val buttonPlus: Button = findViewById(R.id.plus)
        val buttonDivide: Button = findViewById(R.id.divide)
        val buttonMultiply: Button = findViewById(R.id.multiply)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)

            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        buttonEqual.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)

    }

    private fun performOperation(value: Double, operation: String) {
        if  (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN // Handle attempt to divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if (savedInstanceState.getBoolean(OPERAND_BOOLEAN, false)) {
            savedInstanceState.getDouble(OPERAND_CONTENTS)
        } else {
            null
        }
        pendingOperation = savedInstanceState.getString(OPERATION_CONTENTS, "")
        displayOperation.text = pendingOperation
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        Copied section 5 resolution. Didn't noted the error that rotating without input (operand1 = null) crashes the app
//        outState.putString(OPERAND_CONTENTS, operand1?.toString())
        if (operand1 != null) {
            outState.putDouble(OPERAND_CONTENTS, operand1!!)
            outState.putBoolean(OPERAND_BOOLEAN, true)
        }
        outState.putString(OPERATION_CONTENTS, pendingOperation)
    }
}