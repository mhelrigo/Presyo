package com.mhelrigo.presyo.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mhelrigo.domain.product.entity.Product
import com.mhelrigo.presyo.databinding.ItemProductBinding
import com.mhelrigo.presyo.product.model.ProductModel

class ProductRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductModel) {
            binding.textViewName.text = product.name
            binding.chipCurrentPrice.text = product.displayableCurrentPrice()
            binding.chipPreviousPrice.text = product.displayablePreviousPrice()
            binding.imageViewTrend.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    product.trendImage()
                )
            )
            binding.chipImported.visibility =
                product.originViewVisibility()
        }
    }

    private val products: ArrayList<ProductModel> = arrayListOf()

    fun submitProducts(products: List<ProductModel>) {
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