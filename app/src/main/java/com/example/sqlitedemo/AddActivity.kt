package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        addbtn.setOnClickListener{
            if(edtname.text.isEmpty())
            {
                Toast.makeText(this,"Enter Student Name",Toast.LENGTH_SHORT).show()
                edtname.requestFocus()
            }
            else if(edtroll.text.isEmpty()){
                Toast.makeText(this,"Enter Student Roll No",Toast.LENGTH_SHORT).show()
                edtroll.requestFocus()
            }
            else{
                val student=Student()
                student.name=edtname.text.toString()
                student.roll_No=edtroll.text.toString().toInt()
                MainActivity.dbHandler.addStudents(this,student)
                clearField()

            }
        }

        cancelbtn.setOnClickListener{
            clearField()
            finish()
        }
    }
    fun clearField(){
        edtname.text.clear()
        edtroll.text.clear()

    }
}