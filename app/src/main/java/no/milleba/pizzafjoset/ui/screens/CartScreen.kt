package no.milleba.pizzafjoset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.model.CartItem
import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.ui.components.AddButton
import no.milleba.pizzafjoset.ui.components.RemoveButton
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark
import no.milleba.pizzafjoset.ui.utils.drawableIdFromApiPath
import no.milleba.pizzafjoset.ui.viewModels.OrderViewModel
import kotlin.math.roundToInt


@Composable
fun CartScreen(orderViewModel: OrderViewModel) {
    val state by orderViewModel.uiState.collectAsStateWithLifecycle()
    val catalog by orderViewModel.mealsById.collectAsStateWithLifecycle()

    val items: List<CartItem> = remember(state.items, catalog) {
        state.items.mapNotNull { (id, qty) ->
            val meal = catalog[id] ?: return@mapNotNull null
            val unit = meal.priceBig ?: meal.price ?: meal.priceSmall ?: 0.0 // FIKSE RIKTIG PRIS
            CartItem(meal = meal, quantity = qty, price = unit)
        }
    }

    Cart(
        items = items,
        subtotal = state.totalPrice,
        onAdd = { orderViewModel.add(it) },
        onRemove = { orderViewModel.removeOne(it) })
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    PizzaFjosetAppTheme {
        val vm = remember { OrderViewModel() }
        CartScreen(orderViewModel = vm)
    }
}


@Composable
fun Cart(items: List<CartItem>, subtotal: Double, onAdd: (Meal) -> Unit, onRemove: (Meal) -> Unit) {
    val kr = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("nb", "NO"))

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(Modifier.height(600.dp)) {
            itemsIndexed(items) { i, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val ctx = androidx.compose.ui.platform.LocalContext.current
                    val resId = drawableIdFromApiPath(
                        item.meal.image,
                        ctx,
                        fallback = R.drawable.placeholder
                    )

                    Image(
                        painter = painterResource(resId),
                        contentDescription = item.meal.title,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, errorContainerDark, RoundedCornerShape(4.dp))
                            .size(96.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                BasicText(
                                    text = item.meal.title.uppercase(),
                                    modifier = Modifier.weight(1f),
                                    style = TextStyle(
                                        color = onSurfaceVariantDark,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                BasicText(
                                    text = "${item.price?.roundToInt()} kr",
                                    maxLines = 1,
                                    modifier = Modifier.padding(start = 16.dp),
                                    style = TextStyle(
                                        color = errorContainerDark,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            BasicText(
                                text = item.meal.category,
                                style = TextStyle(color = onSurfaceVariantDark)
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BasicText(
                                text = "Antall ${item.quantity}",
                                style = TextStyle(color = onSurfaceVariantDark)
                            )
                            AddButton(canAddMeal = true, onClick = { onAdd(item.meal) })
                            RemoveButton(canRemoveMeal = true, onClick = { onRemove(item.meal) })

                        }
                    }
                }
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(onSurfaceVariantDark)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    "Delsum",
                    style = TextStyle(
                        color = onSurfaceVariantDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                BasicText(
                    text = kr.format(subtotal),
                    style = TextStyle(
                        color = onSurfaceVariantDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(role = Role.Button) {  /* TODO: navigate to checkout */ }
                    .background(onSurfaceVariantDark)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center) {
                BasicText(
                    "Gå til betaling",
                    style = TextStyle(
                        color = errorContainerDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

