package com.example.b2023gr2sw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder
        >() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val descripcionTextView: TextView
        val likesTextView: TextView
        val accionButton: Button
        var numeroLikes = 0
        init {
            //Con esto guardamos del text view en las variables de arriba
            nombreTextView = view.findViewById(R.id.tv_nombre)
            descripcionTextView = view.findViewById(R.id.tv_descripcion)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton = view.findViewById(R.id.btn_dar_like)
            //cada que demos likes se sumaran
            accionButton.setOnClickListener { anadirLike() }
        }
        fun anadirLike(){
            numeroLikes =  numeroLikes + 1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
    }
    //El adaptador necesita saer cuantos elementos tiene el arreglo
    override fun getItemCount(): Int {
        return this.lista.size
    }
    // Tenemos que llenarla con una interfaz
    //
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                //hacemos referencia a recycler view vista
                // cual sera el loyout para cada una de la lista.
                R.layout.recycler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    //en el oncreate nosotros seteamos el loyout y en este los vamos a crear
    // y llenar con los datos y pintamos la interfaz
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual
            .descripcion
        holder.likesTextView.text = "0"
        holder.accionButton.text = "ID:${entrenadorActual.id} " +
                "Nombre:${entrenadorActual.nombre}"
    }

}