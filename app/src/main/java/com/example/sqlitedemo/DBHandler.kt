package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(context: Context,name:String?,factory:SQLiteDatabase.CursorFactory?,version:Int): SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME="MyData.db"
        private val DATABASE_VERSION=1
        val TABLE_NAME="Student"
        val ROLL_NO="roll_no"
        val NAME="name"
        val CClass="Class"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_STUDENT_TABLE=("CREATE TABLE $TABLE_NAME(" +
                "$ROLL_NO INTEGER PRIMARY KEY," +
                "$NAME TEXT)")
        db?.execSQL(CREATE_STUDENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    fun getStudents(mCtx:Context) : ArrayList<Student>{
        val qry="Select * From $TABLE_NAME"
        val db:SQLiteDatabase=this.readableDatabase
        val cursor:Cursor=db.rawQuery(qry,null)
        val students=ArrayList<Student>()
        if(cursor.count==0){
            Toast.makeText(mCtx,"No records Found",Toast.LENGTH_SHORT).show()
        }else {
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                val student = Student()
                student.roll_No = cursor.getInt(cursor.getColumnIndex(ROLL_NO))
                student.name=cursor.getString(cursor.getColumnIndex(NAME))
//                student.cclass=cursor.getString(cursor.getColumnIndex(CClass))

                students.add(student)
                cursor.moveToNext()

            }
            Toast.makeText(mCtx,"${cursor.count.toString()}",Toast.LENGTH_SHORT).show()

        }
        cursor.close()
        db.close()
        return students
    }
    fun addStudents(mCtx: Context,student:Student){
        val values=ContentValues()
        values.put(NAME,student.name)
        values.put(ROLL_NO,student.roll_No)
        val db:SQLiteDatabase=this.writableDatabase
        try{
            db.insert(TABLE_NAME,null,values)
//            db.rawQuery("insert into $TABLE_NAME($ROLL_NO,$NAME)values(?,?)" )
            Toast.makeText(mCtx,"Record Inserted",Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
    }


    fun deleteStudent(studentroll:Int):Boolean{
        val qry="Delete From $TABLE_NAME where $ROLL_NO=$studentroll"
        val db:SQLiteDatabase=this.writableDatabase
        var result:Boolean=false
        try{
            //val cursor:Int=db.delete(TABLE_NAME,"$ROLL_NO=?", arrayOf(studentroll.toString()))

            val cursor:Unit=db.execSQL(qry)
            result=true
        }
        catch (e:java.lang.Exception){
            Log.e(ContentValues.TAG,"Error Deleteing")
        }
        db.close()
        return result
    }


    fun updateStudent(studentroll:String,studentName:String):Boolean{
        val db:SQLiteDatabase=this.writableDatabase
        val contentValues=ContentValues()
        var result:Boolean=false
        contentValues.put(ROLL_NO,studentroll)
        contentValues.put(NAME,studentName)
        try {
            db.update(TABLE_NAME,contentValues,"$ROLL_NO=?", arrayOf(studentroll))
            result=true
        } catch (e: Exception) {
            result=false
            Log.e(ContentValues.TAG,"Error Updating")
        }
        return  result
    }

}