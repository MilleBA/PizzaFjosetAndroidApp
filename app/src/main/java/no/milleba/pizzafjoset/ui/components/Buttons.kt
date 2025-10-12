package no.milleba.pizzafjoset.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onErrorContainerLightMediumContrast
import no.milleba.pizzafjoset.ui.theme.onSurfaceLightMediumContrast
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@Composable
fun AddButton(
    canAddMeal: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = canAddMeal,
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = onSurfaceVariantDark,
            contentColor = onSurfaceLightMediumContrast
        )
    )
    {
        // Text(stringResource(R.string.add), fontWeight = FontWeight.Bold)
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
        // Text(stringResource(R.string.remove), fontWeight = FontWeight.Bold)
        Icon(
            painter = painterResource(R.drawable.remove_cart),
            contentDescription = stringResource(R.string.remove)
        )
    }

}