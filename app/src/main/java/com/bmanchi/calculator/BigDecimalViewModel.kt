package com.bmanchi.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException
import java.math.BigDecimal

class BigDecimalViewModel : ViewModel() {

    //variables to hold the operands and type of calculation
    private var operand1: BigDecimal? = null

    // private var operand2:Double = 0.0 //moved for narrow scope
    private var pendingOperation = "="

    private val result = MutableLiveData<BigDecimal>()
    val stringResult: LiveData<String>
        get() = Transformations.map(result) { it.toString() }
    private val newNumber = MutableLiveData<String>()
    val stringNewNumber: LiveData<String>
        get() = newNumber
    private val operation = MutableLiveData<String>()
    val stringOperation: LiveData<String>
        get() = operation

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            newNumber.value = newNumber.value + caption
        } else {
            newNumber.value = caption
        }
    }

    fun operandPressed(op: String) {
        try {
            val value = newNumber.value?.toBigDecimal()
            if (value != null) {
                performOperation(value, op)
            }

        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }
        pendingOperation = op
        operation.value = pendingOperation
    }

    fun negPressed() {
        val value = newNumber.value.toString()
        if (value.isEmpty()) {
            newNumber.value = "-"
        } else {
            try {
                var doubleValue = value.toBigDecimal()
                doubleValue *= BigDecimal.valueOf(-1)
                newNumber.value = doubleValue.toString()
            } catch (e: NumberFormatException) {
                // newNumber was "-" or ".", so clear it
                newNumber.value = ""
            }
        }
    }

    private fun performOperation(value: BigDecimal, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == BigDecimal.valueOf(0.0)) {
                    BigDecimal.valueOf(Double.NaN) // Handle attempt to divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.value = operand1
        newNumber.value = ""
    }
}