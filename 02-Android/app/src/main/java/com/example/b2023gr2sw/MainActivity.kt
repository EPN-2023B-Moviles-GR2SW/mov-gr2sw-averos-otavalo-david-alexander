package com.example.b2023gr2sw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //con esto linkeamos una actividad
        setContentView(R.layout.activity_main)
        //aqui referenciamos al id del boton que tengamos en le main.xml
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener{
            irActividad(ACicloVida::class.java)
        }
        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
    }

        fun irActividad(clase: Class<*>){
            val intent = Intent(this, clase)
            startActivity(intent)
        }
    }