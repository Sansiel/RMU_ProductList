package com.example.mylisttobuy

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
class SqliteDatabase internal constructor(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val createProductTable = ("CREATE TABLE "
                + TABLE_PRODUCT + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NO + " INTEGER" + ")")
        db.execSQL(createProductTable)
    }
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        onCreate(db)
    }
    fun listProduct(): ArrayList<Product> {
        val sql = "select * from $TABLE_PRODUCT"
        val db = this.readableDatabase
        val storeProduct =
            ArrayList<Product>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val name = cursor.getString(1)
                val cost = cursor.getString(2)
                storeProduct.add(Product(id, name, cost))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        return storeProduct
    }
    fun addProduct(product: Product) {
        val values = ContentValues()
        values.put(COLUMN_NAME, product.name)
        values.put(COLUMN_NO, product.cost)
        val db = this.writableDatabase
        db.insert(TABLE_PRODUCT, null, values)
    }
    fun updateProduct(product: Product) {
        val values = ContentValues()
        values.put(COLUMN_NAME, product.name)
        values.put(COLUMN_NO, product.cost)
        val db = this.writableDatabase
        db.update(
            TABLE_PRODUCT,
            values,
            "$COLUMN_ID = ?",
            arrayOf(product.id.toString())
        )
    }
    fun deleteProduct(id: Int) {
        val db = this.writableDatabase
        db.delete(
            TABLE_PRODUCT,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }
    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "ProductList"
        private const val TABLE_PRODUCT = "Product"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NAME = "productName"
        private const val COLUMN_NO = "cost"
    }
}