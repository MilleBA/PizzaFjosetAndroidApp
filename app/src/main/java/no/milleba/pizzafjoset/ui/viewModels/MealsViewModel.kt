package no.milleba.pizzafjoset.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.milleba.pizzafjoset.data.MealsUiState
import no.milleba.pizzafjoset.data.RetrofitClient
import no.milleba.pizzafjoset.model.MealsRepository
import no.milleba.pizzafjoset.model.MealsRepositoryImpl
import no.milleba.pizzafjoset.ui.components.Category

class MealsViewModel(
    private val repo: MealsRepository = MealsRepositoryImpl(RetrofitClient.mealApi)
) : ViewModel() {

    private val _ui = MutableStateFlow(MealsUiState(loading = true))
    val ui: StateFlow<MealsUiState> = _ui.asStateFlow()

    init { loadMeals() }

    fun loadMeals() {
        _ui.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            runCatching { repo.getMeals() }
                .onSuccess { list -> _ui.update { it.copy(meals = list, loading = false) } }
                .onFailure { e -> _ui.update { it.copy(error = e.message, loading = false) } }
        }
    }

    fun selectCategory(cat: Category?) {
        _ui.update { it.copy(selectedCategory = cat) }
    }
}