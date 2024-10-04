package com.example.wizardsapplication.domain

import com.example.wizardsapplication.common.Resource
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsapplication.repo.WizardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWizardsUseCase @Inject constructor(
    private val repository: WizardRepository
) {
    suspend operator fun invoke(): Resource<ArrayList<WizardResponseItem>> {
        var updatedWizards: ArrayList<WizardResponseItem> = arrayListOf()
        val resource = repository.getWizards()
        withContext(Dispatchers.IO) {
            val favoriteList = repository.localData.getAllFavoriteIDs().map { it.id }
            val favoriteSet = favoriteList.toSet()
             updatedWizards = (resource.data?.map { wizard ->
                 wizard.isFavorite = favoriteSet.contains(wizard.id)
                 wizard
             }) as ArrayList<WizardResponseItem>
        }

        return Resource.Success(updatedWizards,resource.message?:"")
    }
}