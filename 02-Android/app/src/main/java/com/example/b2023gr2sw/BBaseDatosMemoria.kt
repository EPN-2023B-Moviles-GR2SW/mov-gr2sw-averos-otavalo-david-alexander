package com.example.b2023gr2sw
class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        //lo llenaremos con datos de prueba
        //Si queremos escribir codigo para luego inicializar algo dentro del companion object
        //debemos ponelo dentro de un bloque de codigo init
        init {
            arregloBEntrenador.add(BEntrenador(1,"David","d@d.com"))
            arregloBEntrenador.add(BEntrenador(2,"Vicente","v@v.com"))
            arregloBEntrenador.add(BEntrenador(3,"Emy","e@e.com"))
        }
    }
}