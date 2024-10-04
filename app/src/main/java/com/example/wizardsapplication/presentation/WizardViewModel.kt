package com.example.wizardsapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardsapplication.data.local.WizardDao
import com.example.wizardsapplication.data.model.Elixir
import com.example.wizardsapplication.data.model.Favorite
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsapplication.domain.GetElixirUseCase
import com.example.wizardsapplication.domain.GetWizardsUseCase
import com.example.wizardsapplication.repo.WizardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardViewModel @Inject constructor(
    private val wizardUseCase: GetWizardsUseCase,
    private val elixirUseCase: GetElixirUseCase,
    val wizardDao: WizardDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(WizardUiState())
    val uiState: StateFlow<WizardUiState> = _uiState.asStateFlow()

    private var favoriteList = listOf<String>()

    init {
        loadFavorites()
        getWizards()
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteList = wizardDao.getAllFavoriteIDs().map { it.id }
        }
    }

    fun toggleFavorite(wizardId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favoriteList.contains(wizardId)

            if (isFavorite) {
                wizardDao.removeFavorite(wizardId)
            } else {
                wizardDao.insertFavorite(Favorite(wizardId))
            }

            val updatedWizardList = uiState.value.wizardList?.map { wizard ->
                if (wizard.id == wizardId) {
                    wizard.copy(isFavorite = !isFavorite)
                } else {
                    wizard
                }
            } ?: emptyList()
            _uiState.update {
                it.copy(wizardList = updatedWizardList)
            }
        }
    }


    fun selectWizard(wizard: WizardResponseItem) {
        _uiState.update {
            it.copy(currentScreen = Screen.WizardDetails, wizardSelectedItem = wizard)
        }
    }

    fun selectElixir(elixir: Elixir) {
        _uiState.update {
            it.copy(currentScreen = Screen.WizardDetails, elixirSelectedItem = elixir)
        }
        getElixirDetails()
    }

    fun onBackClicked(killActivity: () -> Unit) {
        when {
            uiState.value.currentScreen == Screen.WizardDetails -> {
                _uiState.update {
                    it.copy(currentScreen = Screen.WizardList)
                }
            }

            uiState.value.currentScreen == Screen.ElixirDetails -> {
                _uiState.update {
                    it.copy(currentScreen = Screen.WizardDetails)
                }
            }

            else -> {
                killActivity.invoke()
            }
        }
    }

    fun getWizards() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _uiState.update {
                    it.copy(isLoading = true)
                }

                val resource = wizardUseCase.invoke()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        wizardList = resource.data,
                        errorMessage = if (resource.message?.isNotEmpty() == true) resource.message else ""
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Unknown Error"
                    )
                }
            }
        }
    }

    fun getElixirDetails() {
        viewModelScope.launch {
            try {

                _uiState.update {
                    it.copy(isLoading = true)
                }

                val elixirData =
                    uiState.value.elixirSelectedItem?.id?.let { elixirUseCase.invoke(it) }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        elixirDetailItem = elixirData?.data,
                        currentScreen = Screen.ElixirDetails
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Unknown Error"
                    )
                }
            }
        }
    }

}