package no.milleba.pizzafjoset.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@Composable
fun AboutScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "About Screen",
            style = MaterialTheme.typography.titleLarge,
            color = onSurfaceVariantDark
        )
    }
}
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    PizzaFjosetAppTheme {
        AboutScreen()
    }
}