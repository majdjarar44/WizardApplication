package com.example.wizardsapplication.domain

import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse
import com.example.wizardsapplication.common.Resource
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsapplication.repo.WizardRepository
import javax.inject.Inject

class GetElixirUseCase @Inject constructor(
    private val repository: WizardRepository
) {
    suspend operator fun invoke(id:String) : Resource<ElixirResponse> = repository.getElixir(id)
}