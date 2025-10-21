package no.milleba.pizzafjoset.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark


enum class Category(
    val title: String,
    val categories: List<String> = emptyList()
) {
    PIZZA("Pizza", listOf("pizza", "italian", "calzone")),
    BURGER("Burger", listOf("burger", "burgerTuesday")),
    SNACKS("Snacks", listOf("snacks")),
    TOAST("Toast", listOf("toast", "sandwich")),
    SALAT("Salat", listOf("salat")),
    MEAT("Meat", listOf("texMex", "pasta", "aLaCarte")),
    DESSERT("Dessert", listOf("desert")),
    KIDS("Kids", listOf("kidmenu")),
    DRINK("Drink", listOf("drink")),
    EXTRA("Extra", listOf("extra"));
}

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageRes: Int,
    val title: String,
    val color: Color = Color.Transparent
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    selected: Category?,
    onSelected: (Category) -> Unit
) {
    var cats = remember { Category.entries }

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.pizza2, Category.PIZZA.title, onSurfaceVariantDark),
            CarouselItem(1, R.drawable.burger2, Category.BURGER.title, onSurfaceVariantDark),
            CarouselItem(2, R.drawable.cookie, Category.SNACKS.title, onSurfaceVariantDark),
            CarouselItem(3, R.drawable.toast, Category.TOAST.title, onSurfaceVariantDark),
            CarouselItem(4, R.drawable.salat, Category.SALAT.title, onSurfaceVariantDark),
            CarouselItem(5, R.drawable.meal_dinner, Category.MEAT.title, onSurfaceVariantDark),
            CarouselItem(6, R.drawable.icecream, Category.DESSERT.title, onSurfaceVariantDark),
            CarouselItem(7, R.drawable.child, Category.KIDS.title, onSurfaceVariantDark),
            CarouselItem(8, R.drawable.coffee, Category.DRINK.title, onSurfaceVariantDark),
            CarouselItem(9, R.drawable.soup, Category.EXTRA.title, onSurfaceVariantDark),

            )
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp, bottom = 16.dp),
        itemWidth = 50.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        val category = cats[i]
        val isSelected = selected == category

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clickable { onSelected(category) }
                .alpha(if (isSelected) 1f else 0.5f)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = item.color
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    PizzaFjosetAppTheme {
        Categories(
            selected = Category.PIZZA,
            onSelected = {})
    }
}