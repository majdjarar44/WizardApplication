package com.example.wizardsapplication.di


import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.wizardsapplication.repo.WizardRepository
import com.example.wizardsapplication.data.local.WizardDao
import com.example.wizardsapplication.data.local.WizardItemDatabase
import com.example.wizardsapplication.data.remote.WizardApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {


    @Singleton
    @Provides
    fun providePixaPayApi(): WizardApiService {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create()
        ).baseUrl("https://wizard-world-api.herokuapp.com/").build()
            .create(WizardApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideWizardItemDatabase(@ApplicationContext context: Context): WizardItemDatabase {
        return Room.databaseBuilder(
            context,
            WizardItemDatabase::class.java,
            "wizard_item_database"
        ).build()
    }

    @Provides
    fun provideWizardDao(database: WizardItemDatabase): WizardDao {
        return database.wizardItemDao()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideWizardRepository(
        remoteDataSource: WizardApiService,
        localDataSource: WizardDao,
        connectivityManager: ConnectivityManager
    ): WizardRepository {
        return WizardRepository(
            remoteData = remoteDataSource,
            localData = localDataSource,
            connectivityManager = connectivityManager
        )
    }


    private fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }


}