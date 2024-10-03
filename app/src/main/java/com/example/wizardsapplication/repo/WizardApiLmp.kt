package com.example.wizardsapplication.repo


import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse
import com.example.wizardsapplication.common.Resource
import com.example.wizardsapplication.data.model.WizardResponseItem

interface WizardApiLmp {
    suspend fun getWizard(
    ): Resource<ArrayList<WizardResponseItem>>
    suspend fun getElixir(id: String): Resource<ElixirResponse>
}