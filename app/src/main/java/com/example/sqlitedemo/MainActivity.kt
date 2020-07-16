package com.example.sqlitedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var  dbHandler: DBHandler

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHandler= DBHandler(this,null,null,1)

        viewStudents()

        fab.setOnClickListener{
            val i=Intent(this,AddActivity::class.java)
            startActivity(i)
        }
    }
    private fun viewStudents(){
        val studentslist:ArrayList<Student> = dbHandler.getStudents(this)
        val adapter=StudentAdapter(this,studentslist)
        val rv: RecyclerView=findViewById(R.id.recyclerview)
        rv.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter=adapter
    }

    override fun onResume() {
        viewStudents()
        super.onResume()
    }
}