package com.mhelrigo.data.di

import com.mhelrigo.data.product.ProductRepositoryImpl
import com.mhelrigo.data.product.datasource.local.LocalDataSourceImpl
import com.mhelrigo.data.product.datasource.remote.RemoteDataSourceImpl
import com.mhelrigo.domain.product.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {
    @Binds
    abstract fun productRemoteDatasource(remoteDataSourceImpl: RemoteDataSourceImpl): ProductRepository.RemoteDataSource

    @Binds
    abstract fun productLocalDatasource(localDateSourceImpl: LocalDataSourceImpl): ProductRepository.LocalDataSource

    @Binds
    abstract fun productRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}