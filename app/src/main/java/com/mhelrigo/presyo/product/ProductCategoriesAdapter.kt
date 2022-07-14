package com.mhelrigo.presyo.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mhelrigo.domain.product.entity.ProductCategory
import com.mhelrigo.presyo.databinding.ItemProductCategoriesBinding
import com.mhelrigo.presyo.product.model.ProductCategoryModel

class ProductCategoriesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProductCategoriesViewHolder(val binding: ItemProductCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var productCategory: ProductCategoryModel

        fun bind(productCategory: ProductCategoryModel) {
            this.productCategory = productCategory

            binding.textViewName.text = productCategory.name

            setUpRecyclerView()
        }

        private fun setUpRecyclerView() {
            binding.recyclerViewProduct.apply {
                adapter = ProductRecyclerViewAdapter().apply {
                    submitProducts(productCategory.products)
                }

                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private val productCategories: ArrayList<ProductCategoryModel> = arrayListOf()

    fun submitProductCategories(productCategories: List<ProductCategoryModel>) {
        this.productCategories.clear()
        this.productCategories.addAll(productCategories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductCategoriesViewHolder(
            ItemProductCategoriesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductCategoriesViewHolder).bind(productCategories[position])
    }

    override fun getItemCount() = productCategories.size
}