package com.example.wizardsapplication.presentation


import com.example.wizardsapplication.data.model.Elixir
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse

data class WizardUiState (

    val wizardList:List<WizardResponseItem> ?= null,

    val wizardSelectedItem: WizardResponseItem?= null,

    val elixirSelectedItem: Elixir?= null,

    val elixirDetailItem: ElixirResponse?=null,

    val isLoading:Boolean = false,

    val errorMessage:String ?= null,

    val currentScreen:Screen = Screen.WizardList

)