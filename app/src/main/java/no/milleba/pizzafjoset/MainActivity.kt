package no.milleba.pizzafjoset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaFjosetAppTheme (darkTheme = true) {
                PizzaFjosetApp()
            }
        }
    }

}

