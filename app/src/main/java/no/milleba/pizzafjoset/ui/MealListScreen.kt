package no.milleba.pizzafjoset.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.data.RetrofitClient
import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.ui.components.AddButton
import no.milleba.pizzafjoset.ui.components.Categories
import no.milleba.pizzafjoset.ui.components.Category
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark
import no.milleba.pizzafjoset.ui.theme.primaryDarkMediumContrast
import no.milleba.pizzafjoset.ui.utils.drawableIdFromApiPath
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(
) {
    var meals by remember { mutableStateOf<List<Meal>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var selected by remember { mutableStateOf<Meal?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(Category.PIZZA) }

    LaunchedEffect(Unit) {
        runCatching { RetrofitClient.mealApi.getMeals() }
            .onSuccess { resp -> meals = resp.meals }
            .onFailure { e -> error = e.message }
    }

    when {
        error != null -> Text("Feil: $error")
        meals.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Ingen måltider funnet",
                color = onSurfaceVariantDark,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        else -> {
            Column {
                Categories(
                    selected = selectedCategory,
                    onSelected = { selectedCategory = it }
                )

                MealGrid(
                    meals = meals,
                    onMealClick = { id -> selected = meals.firstOrNull { it._id == id } },
                    categories = selectedCategory?.categories
                )

                selected?.let { meal ->
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                    ModalBottomSheet(
                        onDismissRequest = { selected = null },
                        sheetState = sheetState
                    ) {
                        MealDetailModal(meal, onClose = { selected = null })
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealListScreenPreview() {
    PizzaFjosetAppTheme {
        MealListScreen()
    }
}

@Composable
fun MealGrid(
    meals: List<Meal>,
    onMealClick: (String) -> Unit,
    categories: List<String>? = null
) {
    val categorySet = remember(categories) {
        categories?.map { it.lowercase() }?.toSet()
    }

    val filteredMeals = if (categorySet != null && categorySet.isNotEmpty()) {
        meals.filter { m -> m.category.lowercase() in categorySet }
    } else meals

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 16.dp)
            .heightIn(min = 500.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filteredMeals) { meal ->
            val id = meal._id ?: return@items
            MealItemCard(meal) { onMealClick(id) }
        }
    }
}

@Composable
fun MealItemCard(meal: Meal, onClick: () -> Unit) {
    val ctx = LocalContext.current
    val resId = drawableIdFromApiPath(meal.image, ctx, fallback = R.drawable.placeholder)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF232326),
            contentColor = primaryDarkMediumContrast
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(resId),
                contentDescription = meal.title,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            Spacer(Modifier.height(10.dp))

            Text(
                meal.title.uppercase(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(10.dp))

            val displayPrice = meal.priceBig ?: meal.price ?: meal.priceSmall

            displayPrice?.let {
                Text(
                    text = if (meal.priceBig != null) "${meal.priceBig.roundToInt()}/${meal.priceSmall?.roundToInt()} kr" else "${meal.price?.roundToInt()} kr",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = errorContainerDark,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MealDetailModal(meal: Meal, onClose: () -> Unit) {
    val ctx = LocalContext.current
    val resId = drawableIdFromApiPath(meal.image, ctx, fallback = R.drawable.placeholder)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = meal.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = onSurfaceVariantDark
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Lukk", tint = onSurfaceVariantDark)
            }
        }

        Spacer(Modifier.height(12.dp))

        Image(
            painter = painterResource(resId),
            contentDescription = meal.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(12.dp))

        val displayPrice = meal.priceBig ?: meal.price ?: meal.priceSmall

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                displayPrice?.let {
                    Text(
                        text = if (meal.priceBig != null) "${meal.priceBig.roundToInt()}/${meal.priceSmall?.roundToInt()} kr" else "${meal.price?.roundToInt()} kr",
                        style = MaterialTheme.typography.titleMedium,
                        color = errorContainerDark,
                        fontWeight = FontWeight.Bold
                    )
                }
                AddButton(canAddMeal = true, onClick = { /*TODO*/ })
            }

            Spacer(Modifier.height(12.dp))

            meal.description?.takeIf { it.isNotBlank() }?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = onSurfaceVariantDark,
                    textAlign = TextAlign.Start
                )
            }
        }

    }
}

