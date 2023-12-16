package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        var snack = Snackbar.make(findViewById(R.id.btn_ciclo_vida),
            textoGlobal, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("holi boli")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackbar("onStart")
    }
    override fun onResume(){
        super.onResume()
        mostrarSnackbar("onResume")
    }
    override fun onRestart(){
        super.onRestart()
        mostrarSnackbar("onRestart")
    }
    override fun onPause(){
        super.onPause()
        mostrarSnackbar("onPause")
    }
    override fun onStop(){
        super.onStop()
        mostrarSnackbar("onStop")
    }
    override fun onDestroy(){
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        //cuando queramos guardar algo
        outState.run {
            putString("textoGuardado",textoGlobal);
        }

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // nuestro savedInstanceState tiene el estado de instancia guardado
        super.onRestoreInstanceState(savedInstanceState)
        val textoRestaurado: String? = savedInstanceState.getString("textoGuardado")
        //si no es nula guardamos la variable
        if (textoRestaurado!=null){
            mostrarSnackbar(textoRestaurado)
            textoGlobal = textoRestaurado
        }
    }
}
