package no.milleba.pizzafjoset.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.viewModels.OrderViewModel

@Composable
fun FavoritesScreen(orderViewModel: OrderViewModel) {
    val favMeals by orderViewModel.favorites.collectAsStateWithLifecycle()

    if (favMeals.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ingen favoritter enda")
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(favMeals, key = { it._id ?: it.title }) { meal ->
            MealItemCard(meal = meal, orderViewModel = orderViewModel)
        }
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


