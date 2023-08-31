package com.example.learnsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity()
{
    lateinit var btnInsert:Button
    lateinit var btnRead:Button
    lateinit var btnUpdate:Button
    lateinit var btnDelete:Button


    lateinit var etvName:EditText
    lateinit var etvAge:EditText

    lateinit var listTxt:TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context=this
        var DB=DataBaseHelper(context)

        listTxt=findViewById(R.id.listTxt)

        btnInsert=findViewById(R.id.btnInsert)
        btnRead=findViewById(R.id.btnRead)
        btnUpdate=findViewById(R.id.btnUpdate)
        btnDelete=findViewById(R.id.btnDelete)

        etvName=findViewById(R.id.etvName)
        etvAge=findViewById(R.id.etvAge)



        btnInsert.setOnClickListener{
            if (etvName.text.toString().length>0 && etvAge.text.toString().length>0)
            {
                var user=User(etvName.text.toString(),etvAge.text.toString().toInt())
                var DB=DataBaseHelper(context)
                DB.insertData(user)
            }else
           {
                Toast.makeText(context,"Plz Fill The All Details", Toast.LENGTH_LONG).show()
            }
        }

        btnRead.setOnClickListener{
            var data=DB.readData()
            listTxt.text=""
            for (i in 0..(data.size-1))
            {
                listTxt.append("Member Id "+ data.get(i).id.toString()  + " " +"Member Name"+" "+data.get(i).name + " " +data.get(i).age + "\n")
            }
        }

        btnUpdate.setOnClickListener {
            DB.UpdataData()
            btnRead.performClick()

        }
        btnDelete.setOnClickListener {
            DB.deleteData()
            btnUpdate.performClick()

        }


    }
}