package com.example.wizardsapplication.data.remote


import com.example.wizardsMyApplication.data.model.elixir.ElixirResponse
import com.example.wizardsapplication.data.model.WizardResponseItem
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

interface WizardApiService {

    @GET("Wizards")
    suspend fun getWizardList() : Response<ArrayList<WizardResponseItem>>

    @GET("Elixirs/{id}")
    suspend fun getElixirDetails(@Path("id")id:String) : Response<ElixirResponse>

}