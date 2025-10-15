package no.milleba.pizzafjoset.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.burgertirsdag),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Image(
                painter = painterResource(R.drawable.logo3),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PizzaFjosetAppTheme {
        HomeScreen()
    }
}
