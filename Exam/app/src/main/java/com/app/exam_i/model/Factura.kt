package com.app.exam_i.model

import java.time.LocalDate

data class Factura(
    var cedulaCliente: String,
    var fechaEmision: LocalDate,
    var numeroFactura: Int,
    var esPagada: Boolean,
    var totalAPagar: Double
){
    override fun toString(): String {
        if (esPagada)
            return "Cédula Cliente = $cedulaCliente\nFecha de Emisión = $fechaEmision\n# de Factura = $numeroFactura\nPagada = Si esta pagada\nTotal A Pagar = $totalAPagar$\n"
        else
            return "Cédula Cliente = $cedulaCliente\nFecha de Emisión = $fechaEmision\n# de Factura = $numeroFactura\nPagada = No esta pagada\nTotal A Pagar = $totalAPagar$\n"
    }
}