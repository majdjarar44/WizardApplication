package com.example.wizardsapplication.common

import com.example.wizardsapplication.common.Resource
import retrofit2.Response

suspend fun <T> Response<T>.handleApiResponse(): Resource<T> {

    return try {
        // Check if the response was successful
        if (isSuccessful) {
            body()?.let { responseBody ->
                // If the body is not null, return it wrapped in Resource.Success
                Resource.Success(responseBody,"")

            } ?: Resource.Error(message = "Empty response body", data = null)
        } else {
            // If the response was not successful, return an error
            Resource.Error(message = "An Unknown Error occurred", data = null)
        }
    } catch (e: Exception) {
        // If an exception occurs (e.g., network issue), return an error
        Resource.Error(message = "Couldn't reach the server", data = null)
    }

}