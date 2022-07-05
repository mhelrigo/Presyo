package com.mhelrigo.presyo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.presyo.databinding.ItemProductBinding
import com.mhelrigo.presyo.main.model.ProductModel

class ProductRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textViewName.text = product.name
            binding.chipCurrentPrice.text =
                "current ₱ ${ProductModel.displayablePrice(product.currentPrice)}"
            binding.chipPreviousPrice.text =
                "previous ₱ ${ProductModel.displayablePrice(product.previousPrice)}"
            binding.imageViewTrend.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    ProductModel.trendImage(
                        product.previousPrice,
                        product.currentPrice
                    )
                )
            )
            binding.chipImported.visibility =
                ProductModel.originViewVisibility(product.productOrigin)
        }
    }

    private val products: ArrayList<Product> = arrayListOf()

    fun submitProducts(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(products[position])
    }

    override fun getItemCount() = products.size
}