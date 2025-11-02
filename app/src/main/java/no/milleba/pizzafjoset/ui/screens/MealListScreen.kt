package no.milleba.pizzafjoset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.ui.components.AddButton
import no.milleba.pizzafjoset.ui.components.Categories
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark
import no.milleba.pizzafjoset.ui.utils.drawableIdFromApiPath
import no.milleba.pizzafjoset.ui.viewModels.MealsViewModel
import no.milleba.pizzafjoset.ui.viewModels.OrderViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealListScreen(
    orderViewModel: OrderViewModel,
    mealsViewModel: MealsViewModel,
    isLandscape: Boolean = false
) {
    val ui by mealsViewModel.ui.collectAsStateWithLifecycle()

    LaunchedEffect(ui.meals) {
        if (ui.meals.isNotEmpty()) {
            orderViewModel.setCatalog(ui.meals)
        }
    }

    when {
        ui.loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Loading…",
                color = onSurfaceVariantDark,
                style = MaterialTheme.typography.titleLarge
            )
        }

        ui.error != null -> Text("Error: ${ui.error}")
        else -> {
            if (isLandscape) {
                LazyRow(
                    modifier = Modifier.fillMaxHeight(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ui.meals, key = { it._id ?: it.title }) { meal ->
                        MealItemCard(
                            meal = meal,
                            orderViewModel = orderViewModel,
                            isLandscape = true
                        )
                    }
                }
            } else {
                Column {
                    Categories(
                        selected = ui.selectedCategory,
                        onSelected = { mealsViewModel.selectCategory(it) }
                    )

                    val filtered by remember(ui.meals, ui.selectedCategory) {
                        val cats = ui.selectedCategory?.categories?.map { it.lowercase() }?.toSet()
                        mutableStateOf(
                            if (cats.isNullOrEmpty()) ui.meals
                            else ui.meals.filter { it.category.lowercase() in cats }
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        items(filtered, key = { it._id ?: it.title }) { meal ->
                            MealItemCard(
                                meal = meal,
                                orderViewModel = orderViewModel
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun MealItemCard(meal: Meal, orderViewModel: OrderViewModel, isLandscape: Boolean = false) {
    val ctx = LocalContext.current
    val resId = drawableIdFromApiPath(meal.image, ctx, fallback = R.drawable.placeholder)
    val favoriteIds by orderViewModel.favoriteIds.collectAsStateWithLifecycle()
    val isFav = meal._id in favoriteIds
    var isOpen by remember { mutableStateOf(false) }


    if (isLandscape) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 6.dp, vertical = 6.dp)
                .width(280.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF232326)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = meal.title,
                    modifier = Modifier
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = meal.title.uppercase(),
                        style = MaterialTheme.typography.titleSmall,
                        color = onSurfaceVariantDark,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = { orderViewModel.toggleFavorite(meal) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFav) "Remove from favorites" else "Add to favorites",
                            tint = if (isFav) errorContainerDark else onSurfaceVariantDark
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val displayPrice = meal.priceBig ?: meal.price ?: meal.priceSmall
                    displayPrice?.let {
                        Text(
                            text = "${it.roundToInt()} kr",
                            color = errorContainerDark,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    AddButton(
                        canAddMeal = true,
                        onClick = { orderViewModel.add(meal) }
                    )
                }
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF232326)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(resId),
                        contentDescription = meal.title,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = meal.title.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = onSurfaceVariantDark,
                            modifier = Modifier.weight(1f),
                            overflow = TextOverflow.Ellipsis
                        )

                        IconButton(
                            onClick = { orderViewModel.toggleFavorite(meal) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFav) "Remove from favorites" else "Add to favorites",
                                tint = if (isFav) errorContainerDark else onSurfaceVariantDark
                            )
                        }

                        MealItemArrow(
                            isOpen = isOpen,
                            onClick = { isOpen = !isOpen }
                        )


                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (isOpen) {

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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val displayPrice = meal.priceBig ?: meal.price ?: meal.priceSmall
                        displayPrice?.let {
                            Text(
                                text = "${it.roundToInt()} kr",
                                color = errorContainerDark,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        AddButton(
                            canAddMeal = true,
                            onClick = { orderViewModel.add(meal) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MealItemArrow(
    isOpen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector =
                if (isOpen) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
            contentDescription =
                if (isOpen) "Close" else "Open",
            tint = onSurfaceVariantDark
        )

    }
}


