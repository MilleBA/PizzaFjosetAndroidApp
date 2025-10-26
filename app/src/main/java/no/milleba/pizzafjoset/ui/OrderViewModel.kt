package no.milleba.pizzafjoset.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import no.milleba.pizzafjoset.data.OrderUiState
import no.milleba.pizzafjoset.model.Meal
import no.milleba.pizzafjoset.model.Order
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class OrderViewModel : ViewModel() {
    private val _items = MutableStateFlow<Map<String, Int>>(emptyMap())
    private val _selectedDate = MutableStateFlow<String?>(null)
    private val mealsById = MutableStateFlow<Map<String, Meal>>(emptyMap())
    private val _pickupOptions = pickupOptions()

    val uiState: StateFlow<OrderUiState> =
        combine(_items, _selectedDate, mealsById) { items, selectedDate, catalog ->
            OrderUiState(
                items = items,
                totalCount = items.values.sum(),
                totalPrice = items.entries.sumOf { (id, qty) ->
                    val meal = catalog[id]
                    val unit = meal?.priceBig ?: meal?.price ?: meal?.priceSmall ?: 0.0
                    unit * qty
                },
                selectedDate = selectedDate,
                pickupOptions = _pickupOptions
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            OrderUiState(pickupOptions = _pickupOptions)
        )


    fun setCatalog(meals: List<Meal>) {
        mealsById.value = meals.associateBy { requireNotNull(it._id) }
    }

    fun add(meal: Meal) {
        val id = requireNotNull(meal._id)
        _items.update { old ->
            val n = (old[id] ?: 0) + 1
            old.toMutableMap().apply { put(id, n) }
        }
    }

    fun removeOne(meal: Meal) {
        val id = requireNotNull(meal._id)

        _items.update { old ->
            val cur = (old[id] ?: 0) - 1
            old.toMutableMap().apply {
                if (cur > 0) {
                    put(id, cur)
                } else {
                    remove(id)
                }
            }
        }
    }


    fun toOrder(): Order {
        val catalog = mealsById.value
        val mealsList: List<Meal> =
            _items.value.flatMap { (id, qty) -> List(qty) { catalog.getValue(id) } }
        return Order(cart = mealsList)
    }


    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun clearCart() {
        _items.value = emptyMap()
    }


}

