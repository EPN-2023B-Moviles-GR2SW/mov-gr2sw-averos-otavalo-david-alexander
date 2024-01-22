package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {
    var totalLikes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)
        inicializarRecyclerView()
    }
    // Funcion para inicializar el recycler view
    fun inicializarRecyclerView(){
        //agarrados desde la interfaz
        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adaptador = FRecyclerViewAdaptadorNombreDescripcion(
            this, // Contexto
            BBaseDatosMemoria.arregloBEntrenador, // Arreglo datos
            recyclerView // Recycler view
        )
        //lo guardamos en el adaptador
        recyclerView.adapter = adaptador
        //ponemos animaciones
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes(){
        totalLikes = totalLikes + 1
        val totalLikesTextView = findViewById<TextView>(
            R.id.tv_total_likes
        )
        totalLikesTextView.text = totalLikes.toString()
    }
}