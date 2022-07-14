package com.mhelrigo.data.product.datasource.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mhelrigo.data.product.datasource.remote.model.ProductCategoriesFirebaseModel
import com.mhelrigo.domain.product.entity.ProductCategories
import com.mhelrigo.domain.product.repository.ProductRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.net.SocketTimeoutException
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) :
    ProductRepository.RemoteDataSource {
    companion object {
        private const val TIME_OUT_MS = 10_000L
    }

    /**
     * When data is receive or if error encountered while getting said data this is set to false.
     * */
    private var isTimeout: AtomicBoolean = AtomicBoolean(true)

    private fun cancelTimeout() {
        isTimeout.set(false)
    }

    private fun isTimeout() = isTimeout.get()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getProducts(): Flow<ProductCategories> {
        val databaseReference =
            firebaseDatabase.getReferenceFromUrl("https://bantay-presyo-default-rtdb.firebaseio.com/")

        return withContext(Dispatchers.IO) {
            callbackFlow {
                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value == null) {
                            close(IllegalArgumentException())
                            return
                        }

                        runCatching {
                            val snapShotResult = snapshot.getValue(
                                ProductCategoriesFirebaseModel::class.java
                            )!!

                            val productCategories =
                                snapShotResult.transform(snapShotResult.data?.map {
                                    it.transform(it.product?.map {
                                        it.transform()
                                    }!!)
                                }!!)

                            trySend(productCategories)
                        }.onFailure {
                            close(IllegalArgumentException())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        cancel("Something went wrong", CancellationException())
                    }
                }

                databaseReference.addValueEventListener(valueEventListener)

                configureRequestTimeOut(this, isTimeout())

                awaitClose {
                    // manual timeout is canceled because the request returned a result.
                    cancelTimeout()

                    databaseReference.removeEventListener(valueEventListener)
                }
            }
        }
    }

    /**
     * Firebase doesn't provide a Request Timeout. Since firebase will continue with the request
     * even without internet connection, this simple method will cause a simulated Request Timeout.
     * */
    private suspend fun configureRequestTimeOut(
        scope: ProducerScope<ProductCategories>,
        cancel: Boolean
    ) {
        // basically runs a timer for TIME_OUT_MS amount in seconds.
        delay(TIME_OUT_MS)
        // if allowed,
        // causes a SocketTimeoutException because it's running for quite some time.
        if (cancel) {
            scope.close(SocketTimeoutException())
        }
    }
}