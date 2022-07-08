package com.mhelrigo.data.product.datasource.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mhelrigo.data.product.datasource.remote.model.ProductCategoriesFirebaseModel
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) :
    ProductRepository.RemoteDataSource {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getProducts(): Flow<ProductCategories> {
        val databaseReference =
            firebaseDatabase.getReferenceFromUrl("https://bantay-presyo-default-rtdb.firebaseio.com/")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value == null) {
                        close(IllegalArgumentException())
                    } else {
                        val snapShotResult = snapshot.getValue(
                            ProductCategoriesFirebaseModel::class.java
                        )!!

                        val productCategories = snapShotResult.transform(snapShotResult.data?.map {
                            it.transform(it.product?.map {
                                it.transform()
                            }!!)
                        }!!)

                        trySend(productCategories)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel("Something went wrong", CancellationException())
                }
            }

            databaseReference.addValueEventListener(valueEventListener)

            awaitClose {
                databaseReference.removeEventListener(valueEventListener)
            }
        }
    }
}