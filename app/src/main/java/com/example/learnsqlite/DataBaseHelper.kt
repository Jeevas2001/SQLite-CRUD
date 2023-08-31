package com.example.learnsqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME="MyDB"
val TABLE="MyDB"
val COL_NAME="name"
val COL_AGE="age"
val COL_ID="id"

class DataBaseHelper(var context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1)
{
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(db: SQLiteDatabase?)
    {
        val createTable=" CREATE TABLE "+ TABLE + "(" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_AGE+" INTEGER)"

            db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query="DROP TABLE IF EXISTS"+ TABLE
        db?.execSQL(query)
    }

    fun insertData(user:User)
    {
        val db =this.writableDatabase
        var cv =ContentValues()
        cv.put(COL_NAME,user.name)
        cv.put(COL_AGE,user.age)
        val result=db.insert(TABLE,null,cv)
        if (result==-1.toLong())
        {
            Toast.makeText(context,"Something Wrong",Toast.LENGTH_LONG).show()
        }else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()

        }
    }

    @SuppressLint("Range")
    fun readData():MutableList<User>
    {
        var list:MutableList<User> =ArrayList()
        var db=this.writableDatabase
       var query=" Select * From "+ TABLE
        var result =db.rawQuery(query,null)

        if (result.moveToFirst())
        {
            do
            {
                var user = User()
                user.id=result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name=result.getString(result.getColumnIndex(COL_NAME))
                user.age=result.getString(result.getColumnIndex(COL_AGE)).toInt()
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun deleteData()
    {

        val db=this.writableDatabase
        db.delete(TABLE, null,null)
        db.close()


    }

    @SuppressLint("Range")
    fun UpdataData()
    {
        var db=this.writableDatabase
        var query=" Select * From "+ TABLE
        var result =db.rawQuery(query,null)

        if (result.moveToFirst())
        {
            do
            {
                var cv=ContentValues()
                cv.put(COL_AGE,result.getInt(result.getColumnIndex(COL_AGE))+1)
               db.update(TABLE,cv, COL_ID + "=? AND "+ COL_NAME + "=?",
                   arrayOf(result.getString(result.getColumnIndex(COL_ID))
                       ,result.getString(result.getColumnIndex(COL_NAME))))

            }while (result.moveToNext())
        }
        result.close()
        db.close()

    }

}