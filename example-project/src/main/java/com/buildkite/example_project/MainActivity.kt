package com.buildkite.example_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.buildkite.example_project.ui.theme.ExampleProjectTheme
import com.buildkite.instrumented_test_collector.InstrumentedTestCollector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val instrumentedTestCollector = InstrumentedTestCollector()
                        
                        Greeting(instrumentedTestCollector.greetingMessage())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(message: String) {
    Text(text = message)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExampleProjectTheme {
        Greeting("Android")
    }
}