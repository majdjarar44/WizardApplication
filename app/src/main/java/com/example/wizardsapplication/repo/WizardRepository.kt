package com.example.wizardsapplication.repo

import android.net.ConnectivityManager
import android.util.Log
import com.example.wizardsapplication.data.model.WizardResponseItem
import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse
import com.example.wizardsapplication.common.Resource
import com.example.wizardsapplication.common.handleApiResponse

import com.example.wizardsapplication.data.local.WizardDao
import com.example.wizardsapplication.data.remote.WizardApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

val Tag = "WizardRepository"

class WizardRepository @Inject constructor(
    val remoteData: WizardApiService,
    val localData: WizardDao,
    val connectivityManager: ConnectivityManager,
) {

    suspend fun getWizards(): Resource<ArrayList<WizardResponseItem>> {

        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork?.isConnected == true

        return if (isConnected) {
            try {
                val response = remoteData.getWizardList()
                return response.handleApiResponse().also { resource ->
                    if (resource is Resource.Success) {
                        resource.data?.let {
                            withContext(Dispatchers.IO) {
                                saveToLocal(it)
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                getLocalData()
            }
        } else {

            getLocalData()
        }
    }


    private suspend fun saveToLocal(data: ArrayList<WizardResponseItem>) {
        try {
            withContext(Dispatchers.IO) {
                localData.insertAll(data)
            }
            Log.d(Tag, "Data successfully saved to local database")
        } catch (e: Exception) {
            Log.e(Tag, "Error saving data to local: ${e.localizedMessage}")
        }
    }

    private suspend fun getLocalData(): Resource<ArrayList<WizardResponseItem>> {
        return try {
            val localDataList = withContext(Dispatchers.IO) {
                localData.getAllWizards()
            }
            Log.d(Tag, "Data fetched from local: $localDataList")
            Resource.Success(ArrayList(localDataList), "No Have Internet Connection")
        } catch (e: Exception) {
            Log.e(Tag, "Error fetching from local database: ${e.localizedMessage}")
            Resource.Error("Error fetching from local database", null)
        }
    }

    suspend fun getElixir(id: String): Resource<ElixirResponse> {
        return remoteData.getElixirDetails(id).handleApiResponse()
    }

}