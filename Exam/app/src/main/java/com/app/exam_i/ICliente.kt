package com.app.exam_i

import com.app.exam_i.model.Factura

class ICliente (public var cedula: String? = null,
        public var numeroAfiliado: Int? = null,
        public var altura: Double? = null,
        public var fechaCumpleanos: String? = null,
        public var sexo: Boolean? = null,
        public var facturas: MutableList<Factura>? = null) {
    override fun toString(): String {
        if(sexo!!){
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Masculino\n"
        }else{
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Femenino\n"
        }
    }
}