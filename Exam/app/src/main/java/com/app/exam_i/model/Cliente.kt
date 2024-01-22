package com.app.exam_i.model

import java.time.LocalDate

data class Cliente (
    var cedula: String,
    var numeroAfiliado: Int,
    var altura: Double,
    var fechaCumpleanos: LocalDate,
    var sexo: Boolean,
    var facturas: MutableList<Factura> = mutableListOf()
){
    override fun toString(): String {
        if(sexo){
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Masculino\n"
        }else{
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Femenino\n"
        }
    }
}
