package com.example.adivinharpalavras

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "peril.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Users"
        private const val COLUMN_ID = "ID"
        private const val COLUMN_NAME = "Name"
        private const val COLUMN_POINTS = "Points"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
        CREATE TABLE $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_NAME TEXT NOT NULL,
        $COLUMN_POINTS INTEGER DEFAULT 300 ) """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addUser(nome: String) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nome)
            put(COLUMN_POINTS, 300)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun deletePerfil(nome: String) : Boolean {
        val db = this.writableDatabase
        val whereClause = "name = ?"
        val whereArgs = arrayOf(nome)
        val rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs)
        return rowsDeleted > 0
    }

    fun selectPerfil(): List<Perfil> {
        val db = this.readableDatabase
        val select = "SELECT Name, Points FROM $TABLE_NAME"
        val cursor = db.rawQuery(select, null)
        val perfilList = mutableListOf<Perfil>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
                val points = cursor.getString(cursor.getColumnIndexOrThrow("Points"))
                perfilList.add(Perfil(name, points))
            } while (cursor.moveToNext())
        }
        cursor.close()
        Log.d("Database", "Total profiles loaded: ${perfilList.size}")
        return perfilList
    }

    fun existsPerfil(nome: String) : Boolean{
        val db = this.readableDatabase
        val select = "SELECT $COLUMN_NAME FROM $TABLE_NAME WHERE $COLUMN_NAME = ?"
        val cursor = db.rawQuery(select, arrayOf(nome))

        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return exists
    }

}