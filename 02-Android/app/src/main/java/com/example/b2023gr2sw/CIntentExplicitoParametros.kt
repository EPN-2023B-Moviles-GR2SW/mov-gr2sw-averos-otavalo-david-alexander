package com.example.b2023gr2sw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        //para obtener las variables que nos enviaros podemos utilizar intent.getStringExtra
        //no es necesario importar el intent
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener { devolverRespuesta() }
    }
    //para devolver datos y para cerrar la actividad
    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        //Estos datos son los que se devuelven
        intentDevolverParametros.putExtra("nombreModificado", "Vicente")
        intentDevolverParametros.putExtra("edadModificado", 33)
        //para poner los datos es el setResult
        setResult(
            //aqui podemos si esta todo ok
            RESULT_OK,
            intentDevolverParametros
        )
        //para que se cierre visualmenete TENEMOS QUE poner el finish()
        finish()
    }
}