package com.app.alarmyapp.model

import java.time.LocalTime

data class Alarma (val hora: LocalTime, val activa: Boolean, val descripcion: String,
                   val tono: String, val vibracion: Boolean, val repeticion: Int,
                   val dias: List<String>, val mision: String){

}