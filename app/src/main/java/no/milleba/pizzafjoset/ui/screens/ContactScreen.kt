package no.milleba.pizzafjoset.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark

@SuppressLint("UnrememberedMutableState")
@Composable
fun ContactScreen() {
    val pos = LatLng(59.41243, 9.06126)
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 15f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContactRow(
                icon = painterResource(R.drawable.domain),
                text = "Bøgata 11\nBø i Telemark, 3800"
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContactRow(
                icon = Icons.Default.Phone,
                text = "35 95 00 12"
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContactRow(
                icon = Icons.Default.MailOutline,
                text = "pizzafjoset@gmail.com"
            )

            Spacer(modifier = Modifier.height(8.dp))

            ContactRow(
                icon = painterResource(R.drawable.clock),
                text = "Mandag-Fredag: 15-22\n" +
                        "Lørdag-Søndag: 13-22\n"
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ) {
            GoogleMap(
                cameraPositionState = camera,
                modifier = Modifier.fillMaxSize(),
                properties = MapProperties(
                    mapType = MapType.HYBRID
                )
            ) {
                Marker(state = MarkerState(pos), title = "Pizza Fjoset")
            }
        }
    }
}

@Composable
private fun ContactRow(
    icon: Any,
    text: String
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        when (icon) {
            is ImageVector -> Icon(
                imageVector = icon,
                contentDescription = null,
                tint = onSurfaceVariantDark,
                modifier = Modifier.size(24.dp)
            )

            is Painter -> Icon(
                painter = icon,
                contentDescription = null,
                tint = onSurfaceVariantDark,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = text,
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            color = onSurfaceVariantDark
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    PizzaFjosetAppTheme {
        ContactScreen()
    }
}