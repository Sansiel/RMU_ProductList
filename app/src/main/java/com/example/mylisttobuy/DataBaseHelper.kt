import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.mylisttobuy.ListToBuy

val DATABASENAME = "MY DATABASE"
val TABLENAME = "ListsToBuy"
val COL_NAME = "prodName"
val COL_COST = "cost"
val COL_ID = "id"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_COST + " INTEGER)"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(listToBuy: ListToBuy) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, listToBuy.prodName)
        contentValues.put(COL_COST, listToBuy.cost)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData(): MutableList<ListToBuy> {
        val list: MutableList<ListToBuy> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val listToBuy = ListToBuy()
                listToBuy.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                listToBuy.prodName = result.getString(result.getColumnIndex(COL_NAME))
                listToBuy.cost = result.getString(result.getColumnIndex(COL_COST)).toInt()
                list.add(listToBuy)
            }
            while (result.moveToNext())
        }
        return list
    }
}