package com.app.alarmyapp.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.alarmyapp.R
import com.app.alarmyapp.model.Alarma
import org.w3c.dom.Text

class AlarmaViewHolder(view: View): RecyclerView.ViewHolder(view){
    val listaDias = view.findViewById<TextView>(R.id.txtDiasActivos)
    val hora = view.findViewById<TextView>(R.id.txtHora)
    val horario = view.findViewById<TextView>(R.id.txtHorario)
    val descripcion = view.findViewById<TextView>(R.id.txtDescripcion)
    val mision = view.findViewById<TextView>(R.id.txtMision)
    val activada = view.findViewById<Switch>(R.id.swtActivada)


    fun render(alarmaModel: Alarma){
        hora.text = alarmaModel.hora.toString()
        descripcion.text = alarmaModel.descripcion
        mision.text = alarmaModel.mision
        activada.isChecked = alarmaModel.activa
        listaDias.text = alarmaModel.dias.toString()
        if(alarmaModel.repeticion == 1){
            horario.text = "a. m."
        }else{
            horario.text = "p. m."
        }
    }
}