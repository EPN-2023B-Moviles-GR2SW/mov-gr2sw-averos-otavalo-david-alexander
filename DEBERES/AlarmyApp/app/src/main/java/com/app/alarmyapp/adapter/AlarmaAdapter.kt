package com.app.alarmyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.alarmyapp.model.Alarma
import com.app.alarmyapp.R

class AlarmaAdapter(private val alarmaList: List<Alarma>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            alarmaList.size + 1 -> TYPE_FOOTER // El último ítem será el pie de página
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(layoutInflater.inflate(R.layout.header_app, parent, false))
            TYPE_FOOTER -> FooterViewHolder(layoutInflater.inflate(R.layout.footer_app, parent, false))
            else -> AlarmaViewHolder(layoutInflater.inflate(R.layout.item_alarma, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlarmaViewHolder -> {
                val item = alarmaList[position - 1] // Ajustar por el encabezado
                holder.render(item)
            }
            // No es necesario hacer nada en HeaderViewHolder y FooterViewHolder si solo están mostrando contenido estático
        }
    }

    override fun getItemCount(): Int {
        return alarmaList.size + 2 // Agrega 2 para el encabezado y el pie de página
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Configuración del encabezado aquí
    }

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Configuración del pie de página aquí
    }
}
