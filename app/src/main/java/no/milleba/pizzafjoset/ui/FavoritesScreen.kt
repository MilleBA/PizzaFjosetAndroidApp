package no.milleba.pizzafjoset.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@Composable
fun FavoritesScreen(orderViewModel: OrderViewModel) {
    val state by orderViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorites Screen",
            style = MaterialTheme.typography.titleLarge,
            color = onSurfaceVariantDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    PizzaFjosetAppTheme {
        val vm = remember { OrderViewModel() }
        FavoritesScreen(orderViewModel = vm)
    }
}