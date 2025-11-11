package no.milleba.pizzafjoset.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.net.toUri
import no.milleba.pizzafjoset.R

private val NAME_MAP = mapOf(
    "bø" to R.drawable.bo,
    "pubFries" to R.drawable.fries,
    "cajunPasta" to R.drawable.cajun,
    "salatKylling" to R.drawable.salatkylling,
    "breisås" to R.drawable.breisaas,
    "nachoSalsa" to R.drawable.nachosalsa,
    "salatSpekeskinke" to R.drawable.salatspekeskinke,
    "sprøKyllingBurger" to R.drawable.sproburger,
    "grillpølse" to R.drawable.grillpolse,
    "barnepizzaSkinke" to R.drawable.barnepizzaskinke,
    "kyllingsandwich" to R.drawable.kyllingsandwich,
    "baconFries" to R.drawable.baconfries,
    "sommerburger" to R.drawable.sommerburger,
    "løkringer" to R.drawable.lokringer,
    "pizza" to R.drawable.pizza,
    "barnepizzaOst" to R.drawable.barnepizzaost,
    "fishAndChipsSandwich" to R.drawable.fishandchip,
    "kremetPasta" to R.drawable.kremetpasta,
    "karbonadeSandwich" to R.drawable.karbonade,
    "kyllingNøttesaus" to R.drawable.kyllingnottesaus,
    "hørte" to R.drawable.horte,

    )

@SuppressLint("DiscouragedApi")
fun drawableIdFromApiPath(path: String?, context: Context, fallback: Int): Int {
    if (path.isNullOrBlank()) return fallback

    val base = path.toUri().lastPathSegment
        ?.substringBeforeLast('.')
        ?.lowercase() ?: return fallback

    NAME_MAP[base]?.let { return it }

    val id = context.resources.getIdentifier(base, "drawable", context.packageName)
    return if (id != 0) id else fallback
}
