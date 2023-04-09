package com.telphatech.ecommerce.roomDB

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    val productId: String,

    @ColumnInfo(name = "productName")
    val productName: String? = "",

    @ColumnInfo(name = "productImage")
    val productImage: String? = "",

    @ColumnInfo(name = "productSP")
    val productSP: String? = "",
)