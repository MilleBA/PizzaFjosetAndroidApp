package no.milleba.pizzafjoset.ui.components

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onErrorContainerLightMediumContrast
import no.milleba.pizzafjoset.ui.theme.onSurfaceLightMediumContrast
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@Composable
fun AddButton(
    meal: Meal,
    canAddMeal: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    val context = LocalContext.current

    Button(
        enabled = canAddMeal,
        onClick = {
            onClick()
            Toast.makeText(
                context,
                "${meal.title.uppercase()} lagt til i handlekurven!",
                Toast.LENGTH_SHORT
            ).show()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = onSurfaceVariantDark,
            contentColor = onSurfaceLightMediumContrast
        )
    )
    {
        Icon(
            painter = painterResource(R.drawable.add_cart),
            contentDescription = stringResource(R.string.add)
        )
    }

}

@Composable
fun RemoveButton(
    canRemoveMeal: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = canRemoveMeal,
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = errorContainerDark,
            contentColor = onErrorContainerLightMediumContrast
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.remove_cart),
            contentDescription = stringResource(R.string.remove)
        )
    }

}