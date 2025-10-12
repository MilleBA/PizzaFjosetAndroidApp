package no.milleba.pizzafjoset.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageRes: Int,
    val title: String,
    val color: Color = Color.Transparent
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories() {


    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.pizza2, "Pizza", onSurfaceVariantDark),
            CarouselItem(1, R.drawable.burger2, "Burger", onSurfaceVariantDark),
            CarouselItem(2, R.drawable.snacks, "Snacks"),
            CarouselItem(3, R.drawable.burger, "Burger"),
            CarouselItem(4, R.drawable.italiensk, "Pizza"),
            CarouselItem(5, R.drawable.biffsnadder, "A La Carte"),
            CarouselItem(6, R.drawable.bbq, "BBQ"),
            CarouselItem(7, R.drawable.dessert, "Dessert"),
            CarouselItem(8, R.drawable.barn, "Barnemeny"),
            CarouselItem(9, R.drawable.drink, "Drink"),
            CarouselItem(10, R.drawable.dressing, "Ekstra"),
        )
    }

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 32.dp, bottom = 16.dp),
        itemWidth = 50.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        Image(
            modifier = Modifier
                .height(50.dp)
                .maskClip(MaterialTheme.shapes.medium),
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
        )
    }

}