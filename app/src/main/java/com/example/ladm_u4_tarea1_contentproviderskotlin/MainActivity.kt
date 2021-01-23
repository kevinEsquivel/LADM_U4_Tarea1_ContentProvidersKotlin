package com.example.ladm_u4_tarea1_contentproviderskotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val siLlamada = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnListarLlamadas.setOnClickListener {
            ListarLlamadasPerdidas()
        }
        btnSiguiente.setOnClickListener {
            var intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

    }

    private fun ListarLlamadasPerdidas() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG), siLlamada)
        } else {
            var llamadas = ArrayList<String>()
            var selection: String = CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE
            var cursor: Cursor? = null

            try {
                cursor = contentResolver.query(Uri.parse("content://call_log/calls"), null, null, null, null)
                var registro=""
                while (cursor?.moveToNext()!!){
                    val nombre:String= cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
                    val numero:String= cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
                    registro= "Nombre: "+nombre+"\n Numero: "+numero
                    llamadas.add(registro)
                }

            } catch (ex: Exception) {
                Toast.makeText(this, "Error" + ex, Toast.LENGTH_LONG).show()
            }finally {
                listaLlamadas.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,llamadas)
                cursor?.close()
            }
        }
    }

}