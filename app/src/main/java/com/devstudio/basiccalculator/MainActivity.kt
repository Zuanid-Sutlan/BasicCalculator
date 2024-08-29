package com.devstudio.basiccalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devstudio.basiccalculator.ui.components.MyButton
import com.devstudio.basiccalculator.ui.theme.BasicCalculatorTheme
import com.devstudio.basiccalculator.ui.theme.primaryButton
import com.devstudio.basiccalculator.ui.theme.secondaryButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var value by rememberSaveable { mutableStateOf("") }
    var operator by rememberSaveable { mutableStateOf<Char?>(null) }
    var lastValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Display
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = value,
            textAlign = TextAlign.End,
            fontSize = 42.sp
//            value = value,
//            readOnly = true,
//            onValueChange = { value = it },
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.Transparent,
//                unfocusedContainerColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            )
        )

        // Row for AC, +/-, %, /
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(modifier = Modifier.weight(1f), text = "C", color = secondaryButton, onClicked = {
                value = ""
                operator = null
                lastValue = ""
            }
            )
            MyButton(modifier = Modifier.weight(1f), text = "+/-", color = secondaryButton, fontSize = 16, onClicked = {
                if (value.isNotEmpty() && value != "0") {
                    value = if (value.startsWith("-")) value.drop(1) else "-$value"
                }
            }
            )
            MyButton(modifier = Modifier.weight(1f), text = "%", color = secondaryButton, onClicked = {
                if (value.isNotEmpty()) {
                    value = (value.toDouble() / 100).toString()
                }
            }
            )
            MyButton(modifier = Modifier.weight(1f), text = "/", color = primaryButton, onClicked = {
                handleOperator("/", value, operator, lastValue, onValueChange = { newValue ->
                    lastValue = newValue
                    operator = '/'
                    value = ""
                })
            }
            )
        }

        // Row for 7, 8, 9, x
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(modifier = Modifier.weight(1f), text = "7", onClicked =  { value += "7" } )
            MyButton(modifier = Modifier.weight(1f), text = "8", onClicked =  { value += "8" } )
            MyButton(modifier = Modifier.weight(1f), text = "9", onClicked =  { value += "9" } )
            MyButton(modifier = Modifier.weight(1f), text = "x", color = primaryButton, onClicked =  {
                handleOperator("*", value, operator, lastValue, onValueChange = { newValue ->
                    lastValue = newValue
                    operator = '*'
                    value = ""
                })
            }
            )
        }

        // Row for 4, 5, 6, -
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(modifier = Modifier.weight(1f), text = "4", onClicked =  { value += "4" } )
            MyButton(modifier = Modifier.weight(1f), text = "5", onClicked =  { value += "5" } )
            MyButton(modifier = Modifier.weight(1f), text = "6", onClicked = { value += "6" } )
            MyButton(modifier = Modifier.weight(1f), text = "-", color = primaryButton, onClicked =  {
                handleOperator("-", value, operator, lastValue, onValueChange = { newValue ->
                    lastValue = newValue
                    operator = '-'
                    value = ""
                })
            }
            )
        }

        // Row for 1, 2, 3, +
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(modifier = Modifier.weight(1f), text = "1", onClicked =  { value += "1" } )
            MyButton(modifier = Modifier.weight(1f), text = "2", onClicked =  { value += "2" } )
            MyButton(modifier = Modifier.weight(1f), text = "3", onClicked =  { value += "3" } )
            MyButton(modifier = Modifier.weight(1f), text = "+", color = primaryButton, onClicked =  {
                handleOperator("+", value, operator, lastValue, onValueChange = { newValue ->
                    lastValue = newValue
                    operator = '+'
                    value = ""
                })
            }
            )
        }

        // Row for 0, ., =
        Row(modifier = Modifier.fillMaxWidth()) {
            MyButton(modifier = Modifier.weight(2f), text = "0", onClicked =  { value += "0" } )
            MyButton(modifier = Modifier.weight(1f), text = ".", onClicked =  {
                if (!value.contains(".")) {
                    value += "."
                }
            }
            )
            MyButton(modifier = Modifier.weight(1f), text = "=", color = primaryButton, onClicked =  {
                if (operator != null && lastValue.isNotEmpty()) {
                    value = calculateResult(value, operator!!, lastValue)
                    operator = null
                    lastValue = ""
                }
            }
            )
        }
    }
}

private fun handleOperator(
    operatorSymbol: String,
    currentValue: String,
    operator: Char?,
    lastValue: String,
    onValueChange: (String) -> Unit
) {
    if (currentValue.isNotEmpty()) {
        if (operator != null && lastValue.isNotEmpty()) {
            val result = calculateResult(currentValue, operator, lastValue)
            onValueChange(result)
        } else {
            onValueChange(currentValue)
        }
    }
}

private fun calculateResult(value: String, operator: Char, lastValue: String): String {
    val firstValue = lastValue.toDoubleOrNull() ?: return value
    val secondValue = value.toDoubleOrNull() ?: return value

    return when (operator) {
        '+' -> (firstValue + secondValue).toString()
        '-' -> (firstValue - secondValue).toString()
        '*' -> (firstValue * secondValue).toString()
        '/' -> if (secondValue != 0.0) (firstValue / secondValue).toString() else "Error"
        else -> value
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicCalculatorTheme {
        Greeting("Android")
    }
}