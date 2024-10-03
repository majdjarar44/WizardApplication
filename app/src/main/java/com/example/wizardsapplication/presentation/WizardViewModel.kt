package com.example.wizardsapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardsapplication.data.local.WizardDao
import com.example.wizardsapplication.data.model.Elixir
import com.example.wizardsapplication.data.model.Favorite
import com.example.wizardsapplication.data.model.WizardResponseItem
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
    private val repository: WizardRepository,
    val wizardDao: WizardDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(WizardUiState())
    val uiState: StateFlow<WizardUiState> = _uiState.asStateFlow()


    init {
        fetchData()
    }

    fun toggleFavorite(wizardId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteList = wizardDao.getAllFavoriteIDs().map { it.id }
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

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _uiState.update {
                    it.copy(isLoading = true)
                }

                val resource = repository.getWizard()

                val favoriteList = wizardDao.getAllFavoriteIDs().map { it.id }
                val favoriteSet = favoriteList.toSet()
                val updatedWizards: List<WizardResponseItem>? = resource.data?.map { wizard ->
                    wizard.isFavorite = favoriteSet.contains(wizard.id)
                    wizard
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        wizardList = updatedWizards,
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
                val data = uiState.value.elixirSelectedItem?.id?.let { repository.getElixir(it) }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        elixirDetailItem = data?.data,
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