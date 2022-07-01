package com.mhelrigo.presyo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mhelrigo.presyo.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.getProducts()

        mainViewModel.loadingProduct = {
            Timber.e("Loading...")
        }

        mainViewModel.productReceived = {
            Timber.e("Product Received...")
        }

        mainViewModel.errorEncountered = {
            Timber.e("errorEncountered...")
        }
    }
}