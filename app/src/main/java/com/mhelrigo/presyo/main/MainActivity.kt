package com.mhelrigo.presyo.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.presyo.BuildConfig
import com.mhelrigo.presyo.R
import com.mhelrigo.presyo.databinding.ActivityMainBinding
import com.mhelrigo.presyo.settings.ColorMode
import com.mhelrigo.presyo.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    lateinit var productCategoriesAdapter: ProductCategoriesAdapter

    lateinit var informationDialog: AlertDialog
    lateinit var errorDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);

        setContentView(binding.root)

        setEventListeners()
        setDataObservers()
        setUpRecyclerView()

        requestData()
    }

    private fun setEventListeners() {
        binding.imageViewBackgroundColor.setOnClickListener {
            settingsViewModel.modifyColorMode(settingsViewModel.getColorMode())
        }

        binding.imageViewInformation.setOnClickListener {
            informationDialog = MaterialAlertDialogBuilder(this)
                .setTitle("${resources.getString(R.string.information_title)} (version${BuildConfig.VERSION_NAME})")
                .setMessage(R.string.information_message)
                .setPositiveButton(R.string.information_positive_button) { _, _ ->
                    informationDialog.cancel()
                }.show()
        }
    }

    private fun requestData() {
        settingsViewModel.getColorMode().also {
            settingsViewModel.setColorMode(it)
        }

        mainViewModel.getProducts()
    }

    private fun setDataObservers() {
        mainViewModel.loadingProduct = {
            binding.progressBarLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        mainViewModel.productReceived = {
            populateView(it)
        }

        mainViewModel.errorEncountered = {
            informationDialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.information_positive_button) { _, _ ->
                    finish() // this will do for now.
                }.show()
        }

        settingsViewModel.colorMode = {
            configureColorScheme(it == ColorMode.DARK_MODE)
        }
    }

    private fun populateView(productCategories: ProductCategories) {
        productCategoriesAdapter.submitProductCategories(productCategories.productCategories)
        binding.textViewDate.text = "data as of ${productCategories.date}"
    }

    private fun setUpRecyclerView() {
        productCategoriesAdapter = ProductCategoriesAdapter()

        binding.recyclerViewProductCategories.apply {
            adapter = productCategoriesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun configureColorScheme(isNightMode: Boolean) {
        if (isNightMode) {
            if (delegate.localNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            }
            return
        }

        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
    }
}