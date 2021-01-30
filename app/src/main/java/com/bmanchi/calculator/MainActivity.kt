package com.bmanchi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_main.*

//Unused when ViewModel replaces saveinstance
/*
private const val OPERAND_CONTENTS = "Operand contents"
private const val OPERATION_CONTENTS = "Operation contents"
private const val OPERAND_BOOLEAN = "Operation is null"
*/

class MainActivity : AppCompatActivity() {

//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val operation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        viewModel.result.observe(this, Observer<String>{stringResult -> result.setText(stringResult)})
        viewModel.newNumber.observe(this, Observer {stringNumber -> newNumber.setText(stringNumber)})
        viewModel.operation.observe(this, Observer { stringOperation -> operation.text = stringOperation })

        /*result = findViewById(R.id.result)
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
        val buttonNeg: Button = findViewById(R.id.buttonNeg)*/

        val listener = View.OnClickListener { v ->
/*            // Replaced by ViewModel
            val b = v as Button
            newNumber.append(b.text)*/
            viewModel.digitPressed((v as Button).text.toString())
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
        dot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
           /*//Replaced by ViewModel
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)

            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            displayOperation.text = pendingOperation*/
            viewModel.operandPressed((v as Button).text.toString())
        }

        equal.setOnClickListener(opListener)
        plus.setOnClickListener(opListener)
        minus.setOnClickListener(opListener)
        multiply.setOnClickListener(opListener)
        divide.setOnClickListener(opListener)
        buttonNeg.setOnClickListener(opListener)

        buttonNeg.setOnClickListener {
            /*//Replaced by ViewModel
            val value = newNumber.text.toString()
            if (value.isEmpty()) {
                newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *=-1
                    newNumber.setText(doubleValue.toString())
                } catch (e: NumberFormatException) {
                    // newNumber was "-" or ".", so clear it
                    newNumber.setText("")
                }
            }*/
        viewModel.negPressed()
        }
    }
    /*
     * Save Instances replaced with ViewModel
     *
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
    }*/
}