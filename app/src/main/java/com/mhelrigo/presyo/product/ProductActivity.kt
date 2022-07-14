package com.mhelrigo.presyo.product

import android.content.res.Configuration
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.DataType
import com.mhelrigo.presyo.BuildConfig
import com.mhelrigo.presyo.R
import com.mhelrigo.presyo.databinding.ActivityMainBinding
import com.mhelrigo.presyo.product.model.ProductCategoriesModel
import com.mhelrigo.presyo.settings.ColorMode
import com.mhelrigo.presyo.settings.SettingsViewModel
import com.mhelrigo.presyo.utils.NetworkConnectivityHandler
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()
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

        binding.materialButtonRetry.setOnClickListener {
            productViewModel.getProducts(if (NetworkConnectivityHandler.isConnectedToNetwork(this)) DataType.LATEST else DataType.CACHED)
        }
    }

    private fun requestData() {
        settingsViewModel.getColorMode().also {
            settingsViewModel.setColorMode(it)
        }

        productViewModel.getProducts(if (NetworkConnectivityHandler.isConnectedToNetwork(this)) DataType.LATEST else DataType.CACHED)
    }

    private fun setDataObservers() {
        productViewModel.loadingProduct = {
            binding.textViewLoading.visibility = if (it) View.VISIBLE else View.GONE
            binding.materialButtonRetry.visibility = View.GONE
        }

        productViewModel.productReceived = {
            binding.materialButtonRetry.visibility = View.GONE
            populateView(it)
        }

        productViewModel.errorEncountered = {
            errorDialog = MaterialAlertDialogBuilder(this)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.information_positive_button) { _, _ ->
                    binding.materialButtonRetry.visibility = View.VISIBLE
                }.show()
        }

        settingsViewModel.colorMode = {
            configureColorScheme(it == ColorMode.DARK_MODE)
            binding.imageViewBackgroundColor.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (it == ColorMode.DARK_MODE) R.drawable.ic_light_mode else R.drawable.ic_dark_mode
                )
            )
        }
    }

    private fun populateView(productCategories: ProductCategoriesModel) {
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
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            return
        }

        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
    }
}