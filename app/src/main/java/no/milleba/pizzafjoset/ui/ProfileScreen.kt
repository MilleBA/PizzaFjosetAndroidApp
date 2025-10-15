package no.milleba.pizzafjoset.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.milleba.pizzafjoset.R
import no.milleba.pizzafjoset.ui.theme.PizzaFjosetAppTheme
import no.milleba.pizzafjoset.ui.theme.errorContainerDark
import no.milleba.pizzafjoset.ui.theme.onSurfaceVariantDark
import no.milleba.pizzafjoset.ui.theme.scrimLightMediumContrast
import no.milleba.pizzafjoset.ui.theme.tertiaryLightMediumContrast

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.b2),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(onSurfaceVariantDark)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mille Brekke Amundsen",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = errorContainerDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Bakåskollen 9, Bø i Telemark, 3803",
                style = MaterialTheme.typography.bodyLarge,
                color = onSurfaceVariantDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(180.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = scrimLightMediumContrast)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Payment Methods",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = errorContainerDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    PaymentMethodItem("Visa •••• 1234")
                    PaymentMethodItem("Mastercard •••• 9876")
                    PaymentMethodItem("Vipps – connected")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = errorContainerDark),
                    modifier = Modifier.padding(24.dp)

                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_profile),
                        contentDescription = "Edit Profile",
                        tint = onSurfaceVariantDark,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = errorContainerDark),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.order_approve),
                        contentDescription = "My Orders",
                        tint = onSurfaceVariantDark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun PaymentMethodItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.credit_card),
            contentDescription = "Payment Icon",
            tint = onSurfaceVariantDark,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = onSurfaceVariantDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PizzaFjosetAppTheme {
        ProfileScreen()
    }
}
