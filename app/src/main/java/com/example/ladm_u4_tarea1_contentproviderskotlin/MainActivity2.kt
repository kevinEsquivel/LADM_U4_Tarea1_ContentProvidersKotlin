package com.example.ladm_u4_tarea1_contentproviderskotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main2.*
import java.lang.Exception

class MainActivity2 : AppCompatActivity() {
    val siCalendario = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btnListaCalendar.setOnClickListener {
            listarClendarios()
        }
        btnRegresar.setOnClickListener {
            var intent2 = Intent(this,MainActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun listarClendarios() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALENDAR),siCalendario)
        }else{
            var calendarios = ArrayList<String>()
            var projection = arrayOf(CalendarContract.Calendars.NAME)
            var cursorCalendario : Cursor ?= null
            try {
                cursorCalendario = contentResolver.query(
                    CalendarContract.Calendars.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
                )

                while(cursorCalendario?.moveToNext()!!){
                    calendarios.add(cursorCalendario.getString(0))
                }

            }catch (ex:Exception){
                Toast.makeText(this, "Error" + ex, Toast.LENGTH_LONG).show()
            }finally {
                listaCal.adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,calendarios)
                cursorCalendario?.close()
            }

        }
    }
}