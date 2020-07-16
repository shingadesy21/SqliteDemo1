package com.example.sqlitedemo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lo_stud_insert.view.*
import kotlinx.android.synthetic.main.lo_stud_update.view.*

class StudentAdapter(mCtx: Context,val students:ArrayList<Student>): RecyclerView.Adapter<StudentAdapter.ViewHolder>(){

    val mCtx=mCtx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
    val v:View = LayoutInflater.from(parent.context).inflate(R.layout.lo_stud_insert,parent,false)
    return ViewHolder(v)
    }
    override fun getItemCount(): Int {
return students.size   }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {

        val student:Student=students[position]
        holder.txtname.text=student.name
        holder.txtroll.text=student.roll_No.toString()
        holder.btndelete.setOnClickListener{
            val studentName:String=student.name
            var alertDialog: AlertDialog? =AlertDialog.Builder(mCtx).setTitle("Warning")
                .setMessage("Are you sure to Delete record: $studentName ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{dialog,which->
                    if(MainActivity.dbHandler.deleteStudent(student.roll_No)){
                        students.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,students.size)
                        Toast.makeText(mCtx,"$studentName Deleted",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(mCtx,"Error Deleting",Toast.LENGTH_SHORT).show()

                    }
                }).setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_warning)
                .show()
        }
        holder.btnupdate.setOnClickListener{
            val inflater:LayoutInflater= LayoutInflater.from(mCtx)
            val view=inflater.inflate(R.layout.lo_stud_update,null)

            val txtup_name:TextView=view.findViewById(R.id.update_name)
            val txtup_roll:TextView=view.findViewById(R.id.update_roll)
            txtup_name.text=student.name
            txtup_roll.text=student.roll_No.toString()

           val builder: AlertDialog.Builder? =AlertDialog.Builder(mCtx).setTitle("Update Student Info")
               .setView(view)
               .setPositiveButton("Update", DialogInterface.OnClickListener{dialog,which ->
                   val isUpdate:Boolean=MainActivity.dbHandler.updateStudent(
                       student.roll_No.toString(),
                       view.update_name.text.toString())
                   if(isUpdate==true){
                       students[position].name=view.update_name.text.toString()
                       students[position].roll_No=view.update_roll.text.toString().toInt()
                       notifyDataSetChanged()
                       Toast.makeText(mCtx,"Update Successfull",Toast.LENGTH_SHORT).show()
                   }
                   else{
                       Toast.makeText(mCtx,"Error Updating",Toast.LENGTH_SHORT).show()

                   }
               })
               .setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->

               })
            val alert:AlertDialog= builder!!.create()
            alert.show()
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtname=itemView.name_txt
        val txtroll=itemView.roll_txt
        val btnupdate=itemView.update_btn
        val btndelete=itemView.delete_btn
    }

}